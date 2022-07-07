package com.glow.driver.ui.code_verify

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import okhttp3.MultipartBody

class CodeVerifyRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun login(
        mobile: String,phone_code : String
    ) = safeApiCall {
        api.login(mobile, phone_code)
    }

}
