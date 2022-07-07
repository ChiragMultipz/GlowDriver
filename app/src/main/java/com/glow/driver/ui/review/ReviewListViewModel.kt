package com.glow.driver.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.HashMap

class ReviewListViewModel constructor(private val repository: ReviewRepository) : BaseViewModel(repository){

    private val _reviewResponse: MutableLiveData<Resource<ReviewResponse>> = MutableLiveData()
    val reviewResponse: LiveData<Resource<ReviewResponse>>
        get() = _reviewResponse

    suspend fun getUserOrder(
    ) = viewModelScope.launch {
        _reviewResponse.value = Resource.Loading
        _reviewResponse.value = repository.getReview()
    }
}

