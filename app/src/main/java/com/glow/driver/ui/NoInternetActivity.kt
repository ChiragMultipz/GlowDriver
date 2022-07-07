package com.glow.driver.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.glow.driver.utils.Utils
import com.glow.driver.utils.Utils.toast
import com.glow.driver.R
import com.glow.driver.databinding.ActivityNoInternetBinding

class NoInternetActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNoInternetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
    }

    private fun onClickListener() {
        binding.tvTryAgain.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvTryAgain ->{
                if (Utils.isConnected(this)){
                    finish()
                } else {
                    toast("No Internet Connection!")
                }
            }
        }
    }
}