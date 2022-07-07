package com.glow.driver.ui.payment_history


import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import okhttp3.MultipartBody

class PaymentHistoryRepository constructor(private val api : MyApi) : BaseRepository() {

    suspend fun getUserOrders(
        user_id: String
    ) = safeApiCall {
        api.getPaymentHistory()
    }

}
