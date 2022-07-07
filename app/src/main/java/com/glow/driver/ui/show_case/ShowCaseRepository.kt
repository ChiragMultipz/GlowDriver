package com.glow.driver.ui.show_case

import com.glow.driver.network.MyApi
import com.glow.driver.repository.BaseRepository

class ShowCaseRepository constructor(private val api: MyApi) : BaseRepository() {

    suspend fun getBarberPortfolio(
    ) = safeApiCall {
        api.getBarberPortfolio()
    }

    suspend fun deletePortfolioImage(
        portfolioId: String
    ) = safeApiCall {
        api.removePortfolio(portfolioId)
    }
}