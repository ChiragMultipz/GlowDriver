package com.glow.driver.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.glow.driver.R
import com.glow.driver.databinding.ActivityLoginBinding
import com.glow.driver.network.MyApi
import com.glow.driver.network.Resource
import com.glow.driver.ui.code_verify.CodeVerifyActivity
import com.glow.driver.utils.Constants
import com.glow.driver.utils.Prefrences
import com.glow.driver.utils.Utils.toast
import gun0912.tedimagepicker.util.ToastUtil
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    private lateinit var countrySpinnerAdapter: CountrySpinnerAdapter
    private var countryId: String? = null
    var countryList: ArrayList<ResultItem?> = ArrayList()
    var selectedCountry: String = "Select Country"
    var codeCountry: String = ""
    var isFirstTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
        spinnerListener()

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(LoginRepository(MyApi.getInstance()))
        ).get(LoginViewModel::class.java)

        getCountryCode()

        //Country response handler
        viewModel.countryCodeResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    if (it.value.success!!) {
                        countryList.addAll(it.value.result!!)
                        countrySpinnerAdapter =
                            CountrySpinnerAdapter(ToastUtil.context, countryList)
                        var indexOfFirst = 1
//                        if (isFirstTime.equals("YES")) {
                        indexOfFirst = countryList.indexOfFirst { resultItem ->
                            resultItem!!.id.toString() == "232"
                        }
                        binding.spnCity.adapter = countrySpinnerAdapter
                        binding.spnCity.setSelection(indexOfFirst)

                    }
                }
                is Resource.Failure -> {
                    // Utils.handleApiError(binding.root, it)
                }
            }
        }
    }

    private fun getCountryCode() {
        viewModel.viewModelScope.launch {
            viewModel.countryCode()
        }
    }

    private fun setOnClickListener() {
        binding.relGetStarted.setOnClickListener(this)
        binding.ivBackButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.relGetStarted -> goToVerifyPhoneNumScreen()
            R.id.ivBackButton -> finish()
        }
    }

    private fun goToVerifyPhoneNumScreen() {
        if (isValid()) {
            val intent = Intent(this, CodeVerifyActivity::class.java)
            intent.putExtra(Constants.USER_MOBILE_NO, "" + binding.edtPhoneNumber.text)
            startActivity(intent)
        }

    }

    private fun isValid(): Boolean {
        return when {
            TextUtils.isEmpty(binding.edtPhoneNumber.text.toString()) -> {
                this.toast(getString(R.string.err_empty_phone_number))
                false
            }
            binding.edtPhoneNumber.text.toString().length < 10 -> {
                this.toast(getString(R.string.err_valid_phone_number))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun spinnerListener() {
        binding.spnCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing Selected")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                selectedCountry = countryList[position]!!.phonecode!!
                codeCountry = countryList[position]!!.id!!.toString()

                Prefrences.savePreferencesString(this@LoginActivity, Constants.PREFS_CODE, codeCountry)
                Prefrences.savePreferencesString(this@LoginActivity, Constants.SELECTED_COUNTRY_CODE, selectedCountry)
            }

        }
    }
}