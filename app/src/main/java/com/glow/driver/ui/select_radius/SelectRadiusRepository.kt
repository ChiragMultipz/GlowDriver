package com.glow.driver.ui.select_radius

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository

class SelectRadiusRepository (private val api: MyApi) : BaseRepository(){
    suspend fun addDriverRadius(
        params: Map<String,String>
    ) = safeApiCall {
        api.addDriverRadius(params)
    }

}