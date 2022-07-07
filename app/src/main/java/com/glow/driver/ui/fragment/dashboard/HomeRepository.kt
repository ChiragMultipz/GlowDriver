package com.glow.driver.ui.fragment.dashboard
import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import okhttp3.MultipartBody

class HomeRepository constructor(private val api : MyApi): BaseRepository() {

    suspend fun getOffer(
        params: Map<String,String>
    ) = safeApiCall {
        api.getOffer(params)
    }

    /*

    suspend fun getNearestDrivers(
    ) = safeApiCall {
        api.getNearestDriver()
    }

    suspend fun searchDriver(
        params: Map<String,String>
    ) = safeApiCall {
        api.searchDriver(params)
    }*/
}