package com.glow.driver.ui.insight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.CommonResponse
import com.glow.driver.network.Resource
import com.glow.driver.ui.notification.NotificationListRepository
import com.glow.driver.ui.notification.NotificationListResponse
import kotlinx.coroutines.launch

class InsightViewModel constructor(private val repository: InsightRepository) :
    BaseViewModel(repository) {

    private val _graphResponse: MutableLiveData<Resource<GraphResponseData>> = MutableLiveData()
    val graphResponse: LiveData<Resource<GraphResponseData>>
        get() = _graphResponse

    private val _mostBookedServicesResponse: MutableLiveData<Resource<MostBookedServicesResponse>> = MutableLiveData()
    val mostBookedServicesResponse: LiveData<Resource<MostBookedServicesResponse>>
        get() = _mostBookedServicesResponse

    suspend fun getRevenueMapData(
        driverId : String,
        searchString: String
    ) = viewModelScope.launch {
        _graphResponse.value = Resource.Loading
        _graphResponse.value = repository.getRevenueData(driverId, searchString)
    }

    suspend fun getRevenueDataWithoutSearch(
        driverId : String
    ) = viewModelScope.launch {
        _graphResponse.value = Resource.Loading
        _graphResponse.value = repository.getRevenueDataWithoutSearch(driverId)
    }

    suspend fun getMostBookedServices(
    ) = viewModelScope.launch {
        _mostBookedServicesResponse.value = Resource.Loading
        _mostBookedServicesResponse.value = repository.getMostBookedServices()
    }



}