package com.glow.driver.ui.add_portfolio

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository
import okhttp3.MultipartBody

class AddPortfolioRepository constructor(private val api: MyApi) : BaseRepository() {

    suspend fun addPortfolioImage(
        imageList: List<MultipartBody.Part>,
    ) = safeApiCall {
        api.addPortfolioImage(imageList)
    }
}