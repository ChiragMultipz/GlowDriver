package com.glow.driver.ui.terms_privacy

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository

class TermsPrivacyRepository(private val api: MyApi) : BaseRepository() {

    // 2 = Driver
    suspend fun getTermsPolicy(
    ) = safeApiCall {
        api.getTermsPolicy("2")
    }

}