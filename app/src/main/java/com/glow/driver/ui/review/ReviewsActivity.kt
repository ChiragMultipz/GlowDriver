package com.glow.driver.ui.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.glow.driver.R
import com.glow.driver.databinding.ActivityReviewsBinding
import com.glow.driver.extension.visible
import com.glow.driver.network.MyApi
import com.glow.driver.network.Resource
import com.glow.driver.utils.Constants
import com.glow.driver.utils.Prefrences
import com.glow.driver.utils.Utils
import com.glow.driver.utils.Utils.hide
import kotlinx.coroutines.launch

class ReviewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReviewsBinding
    private lateinit var viewModel: ReviewListViewModel
    private lateinit var adapter: ReviewAdapter
    private val arrayListImage = java.util.ArrayList<String>()
    private var driverId : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ReviewModelFactory(
                ReviewRepository(
                    MyApi.getInstanceToken(
                        Prefrences.getPreferences(
                            this,
                            Constants.API_TOKEN
                        )!!
                    )
                )
            )
        ).get(ReviewListViewModel::class.java)
        driverId = Prefrences.getPreferences(this, Constants.USER_ID)
        adapter = ReviewAdapter(this)
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.reviewRecyclerView.adapter = adapter
        setClickListener()
        getReview()

        viewModel.reviewResponse.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.value.success!!) {
                        if (it.value.result != null) {
                            adapter.updateList(it.value.result.filter {
                                    reviewItem ->
                                reviewItem!!.toId!!.toString() == driverId
                            } as List<ResultItem>)
                        }

                        if (adapter.getList().isEmpty()) {
                            binding.tvTotalReview.text = "( 0 )"
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.tvNoData.visibility = View.GONE
                            binding.tvTotalReview.text = "( ".plus(adapter.getList().size).plus(" )")
                        }
                    } else {
                        Toast.makeText(this, it.value.message!!, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Failure -> Utils.handleApiError(binding.root, it) {
                    binding.progressBar.hide()
                    getReview()
                }
            }
        })
    }

    private fun getReview() {
        viewModel.viewModelScope.launch {
            viewModel.getUserOrder()
        }
    }

    private fun setClickListener() {
        binding.ivBackButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButton -> finish()
        }
    }
}