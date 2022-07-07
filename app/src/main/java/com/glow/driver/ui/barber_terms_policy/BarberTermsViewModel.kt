package com.glow.driver.ui.barber_terms_policy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.CommonResponse
import com.glow.driver.network.Resource
import com.glow.driver.ui.notification.NotificationListRepository
import com.glow.driver.ui.notification.NotificationListResponse
import com.google.android.gms.common.internal.service.Common
import kotlinx.coroutines.launch

class BarberTermsViewModel constructor(private val repository: BarberTermsRepository) :
    BaseViewModel(repository) {

    private val _getBarberProfileResponse: MutableLiveData<Resource<BarberProfileResponse>> = MutableLiveData()
    val getBarberProfileResponse: LiveData<Resource<BarberProfileResponse>>
        get() = _getBarberProfileResponse

    private val _updateTermsResponse: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    val updateTermsResponse: LiveData<Resource<CommonResponse>>
        get() = _updateTermsResponse

    suspend fun getBarberProfile (
        barberId : String,
    ) = viewModelScope.launch {
        _getBarberProfileResponse.value = Resource.Loading
        _getBarberProfileResponse.value = repository.getDriverProfile(barberId)
    }

    suspend fun updateTermsPolicy (
        params : Map<String, String>,
    ) = viewModelScope.launch {
        _updateTermsResponse.value = Resource.Loading
        _updateTermsResponse.value = repository.updateTermsPolicy(params)
    }
}