package com.glow.driver.ui.show_case

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glow.driver.ui.booking_details.BookingDetailsRepository
import com.glow.driver.ui.booking_details.BookingDetailsViewModel


class ShowCaseViewModelFactory(private val repository: ShowCaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowCaseViewModel(repository) as T
    }
}