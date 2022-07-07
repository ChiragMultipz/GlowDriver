package com.glow.driver.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.Resource
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HomeRepository) : BaseViewModel(repository) {

    private val _getOfferResponse: MutableLiveData<Resource<HomeOfferResponse>> = MutableLiveData()
    val getOfferResponse: LiveData<Resource<HomeOfferResponse>>
        get() = _getOfferResponse

    suspend fun getOffer(
        params: Map<String, String>
    ) = viewModelScope.launch {
        _getOfferResponse.value = Resource.Loading
        _getOfferResponse.value = repository.getOffer(params)
    }
    /**/

    /*

    suspend fun getNearestDriver(
    ) = viewModelScope.launch {
        _getDriversResponse.value = Resource.Loading
        _getDriversResponse.value = repository.getNearestDrivers()
    }

    suspend fun searchDriver(
        params: Map<String, String>
    ) = viewModelScope.launch {
        _getDriversResponse.value = Resource.Loading
        _getDriversResponse.value = repository.searchDriver(params)
    }*/


}