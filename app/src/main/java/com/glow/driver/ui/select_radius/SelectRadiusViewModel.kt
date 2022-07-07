package com.glow.driver.ui.select_radius

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.CommonResponse
import com.glow.driver.network.Resource
import kotlinx.coroutines.launch

class SelectRadiusViewModel constructor(private val repository: SelectRadiusRepository) :
    BaseViewModel(repository) {

    private val _selectRadiusResponse: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    val selectRadiusResponse: LiveData<Resource<CommonResponse>>
        get() = _selectRadiusResponse


    suspend fun addBarberService(
        params: Map<String,String>
    ) = viewModelScope.launch {
        _selectRadiusResponse.value = Resource.Loading
        _selectRadiusResponse.value = repository.addDriverRadius(params)
    }
}