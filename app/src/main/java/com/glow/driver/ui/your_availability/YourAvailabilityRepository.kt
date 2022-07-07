package com.glow.driver.ui.your_availability

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository

class YourAvailabilityRepository(private val api: MyApi) : BaseRepository() {

    suspend fun getTimesSlots(
    ) = safeApiCall {
        api.getTimesSlots()
    }

    suspend fun addBarberTime(
        dateList : ArrayList<String>,
        slotTimeList : ArrayList<String>,
    ) = safeApiCall {
        api.addBarberTime(dateList, slotTimeList)
    }

}