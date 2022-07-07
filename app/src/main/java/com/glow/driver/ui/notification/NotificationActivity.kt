package com.glow.driver.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.glow.driver.R
import com.glow.driver.databinding.ActivityNotificationBinding
import com.glow.driver.extension.visible
import com.glow.driver.network.MyApi
import com.glow.driver.network.Resource
import com.glow.driver.utils.Constants
import com.glow.driver.utils.Prefrences
import com.glow.driver.utils.Utils
import com.glow.driver.utils.Utils.hide
import kotlinx.coroutines.launch

class NotificationActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel:NotificationListViewModel
    private lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            NotificationListViewModelFactory(
                NotificationListRepository(
                    MyApi.getInstanceToken(Prefrences.getPreferences(this, Constants.API_TOKEN)!!)
                )
            )
        ).get(NotificationListViewModel::class.java)
        adapter = NotificationAdapter(this)
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notificationRecyclerView.adapter = adapter
        readNotification()
        getNotification()
        setClickListener()

        viewModel.readNotificationResponse.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    if (it.value.success!!){

                    } else {
                        Utils.showErrorDialog(this, it.value.message!!)
                    }
                }
                is Resource.Failure -> Utils.handleApiError(binding.root, it) {
                }
            }
        })

        viewModel.notificationListResponse.observe(this, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.hide()
                    Log.d("TAG", "onViewCreated: ".plus(it.value))

                    if (it.value.success!!){
                        adapter.updateList(it.value.result as List<ResultItem>)
                    } else {
                        Utils.showErrorDialog(this, it.value.message!!)
                    }
                    if (it.value.result.isNullOrEmpty()){
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.tvNoData.visibility = View.GONE
                    }
                }
                is Resource.Failure -> Utils.handleApiError(binding.root, it) {
                    binding.progressBar.hide()
                    getNotification()
                }
            }

        })
    }

    private fun readNotification() {
        viewModel.viewModelScope.launch {
            viewModel.readNotification()
        }
    }

    private fun getNotification() {
        viewModel.viewModelScope.launch {
            viewModel.getNotification()
        }
    }

    private fun setClickListener() {
        binding.ivBackButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBackButton -> finish()
        }
    }
}