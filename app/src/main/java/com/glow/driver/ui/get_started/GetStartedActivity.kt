package com.glow.driver.ui.get_started

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.glow.driver.R
import com.glow.driver.databinding.ActivityGetStartedBinding

import com.glow.driver.ui.login.LoginActivity
import com.glow.driver.ui.terms_privacy.TermsPrivacyActivity
import com.glow.driver.utils.Constants
import com.glow.driver.utils.ManagePermissions
import com.glow.driver.utils.Prefrences

class GetStartedActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGetStartedBinding
    private var userId: String? = ""
    private val permissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    val list = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
        managePermissions = ManagePermissions(this, list, permissionsRequestCode)
        userId = Prefrences.getPreferences(this, Constants.USER_ID)
    }

    private fun setClickListeners() {
        binding.relGetStarted.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.tvPrivacy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.relGetStarted -> goToWelcomeScreen()
            R.id.tvPrivacy -> goToPrivacyPolicy("Privacy")
            R.id.tvTerms -> goToPrivacyPolicy("Terms")
        }
    }

    private fun goToWelcomeScreen() {
        Prefrences.savePreferencesString(this, Constants.IS_FIRST_TIME, "yes")
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToPrivacyPolicy(type: String) {
        val intent = Intent(this, TermsPrivacyActivity::class.java)
        intent.putExtra("Type", type)
        startActivity(intent)
    }
}