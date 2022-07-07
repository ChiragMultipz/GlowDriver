package com.glow.driver.ui.login

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository


class LoginRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getCountryCode(
    ) = safeApiCall {
        api.getCountryCode()
    }

}