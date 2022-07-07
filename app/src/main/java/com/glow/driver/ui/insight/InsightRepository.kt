package com.glow.driver.ui.insight

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import java.util.HashMap

class InsightRepository(private val api: MyApi) : BaseRepository() {

    suspend fun getRevenueData(
        driverId : String,
        searchString: String
    ) = safeApiCall {
        api.getRevenueData(driverId, searchString)
    }

    suspend fun getRevenueDataWithoutSearch(
        driverId : String
    ) = safeApiCall {
        api.getRevenueDataWithoutSearch(driverId)
    }

    suspend fun getMostBookedServices(
    ) = safeApiCall {
        api.getMostBookedServices()
    }
}