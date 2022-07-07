package com.glow.driver.ui.payment_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaymentHistoryModelFactory(private val paymentHistoryRepository: PaymentHistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentHistoryViewModel(paymentHistoryRepository) as T
    }

}
