package com.glow.driver.ui.booking_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.HashMap

class BookingDetailsViewModel constructor(private val repository: BookingDetailsRepository) : BaseViewModel(repository){

    private val _orderReviewResponse: MutableLiveData<Resource<OrderReviewResponse>> = MutableLiveData()
    val orderReviewResponse: LiveData<Resource<OrderReviewResponse>>
        get() = _orderReviewResponse

    suspend fun getOrderReview(
        order_id: String?,
        from_id: String?,
        to_id: String?
    ) = viewModelScope.launch {
        _orderReviewResponse.value = Resource.Loading
        _orderReviewResponse.value = repository.getOrderReview(order_id,from_id,to_id)
    }
}

