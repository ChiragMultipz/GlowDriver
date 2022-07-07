package com.glow.driver.ui.add_portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glow.driver.ui.show_case.ShowCaseRepository
import com.glow.driver.ui.show_case.ShowCaseViewModel


class AddPortfolioViewModelFactory(private val repository: AddPortfolioRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPortfolioViewModel(repository) as T
    }
}