package com.glow.driver.ui.barber_terms_policy

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository

class BarberTermsRepository(private val api: MyApi) : BaseRepository() {

    suspend fun getDriverProfile(
        barberId: String,
    ) = safeApiCall {
        api.getDriverProfile(barberId)
    }

    suspend fun updateTermsPolicy(
        params: Map<String, String>,
    ) = safeApiCall {
        api.updateTermsPolicy(params)
    }

}