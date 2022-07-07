package com.glow.driver.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glow.driver.base.BaseViewModel
import com.glow.driver.network.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.HashMap

class ProfileViewModel constructor(private val repository: ProfileRepository) : BaseViewModel(repository){

    private val _updateProfileResponse: MutableLiveData<Resource<ProfileResponseNew>> = MutableLiveData()
    val updateProfileResponse: LiveData<Resource<ProfileResponseNew>>
        get() = _updateProfileResponse

    suspend fun addProfile(
        files: List<MultipartBody.Part>,
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.addProfile(files,params)
    }

    suspend fun updateProfile(
        files: List<MultipartBody.Part>,
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.updateProfileWithPhoto(files,params)
    }

    suspend fun updateProfileWithoutPhoto(
        params: Map<String,String>
    ) = viewModelScope.launch {
        _updateProfileResponse.value = Resource.Loading
        _updateProfileResponse.value = repository.updateProfileWithoutPhoto(params)
    }

    private val _getCityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val getCityResponse: LiveData<Resource<CityResponse>>
        get() = _getCityResponse

    private val _getStateResponse: MutableLiveData<Resource<StateResponse>> = MutableLiveData()
    val getStateResponse: LiveData<Resource<StateResponse>>
        get() = _getStateResponse

    suspend fun getStates(
    ) = viewModelScope.launch {
        _getStateResponse.value = Resource.Loading
        _getStateResponse.value = repository.getStates()
    }

    suspend fun getCity(countryCode: String?) = viewModelScope.launch {
        _getCityResponse.value = Resource.Loading
        _getCityResponse.value = repository.getCities(countryCode)
    }
}

