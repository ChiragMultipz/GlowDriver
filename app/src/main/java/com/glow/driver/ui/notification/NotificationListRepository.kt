package com.glow.driver.ui.notification

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import java.util.HashMap

class NotificationListRepository(private val api: MyApi) : BaseRepository() {

    suspend fun getNotification(
    ) = safeApiCall {
        api.getNotification()
    }

    suspend fun readNotification(
    ) = safeApiCall {
        api.readNotification()
    }

}