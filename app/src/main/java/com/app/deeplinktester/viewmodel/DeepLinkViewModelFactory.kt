package com.app.deeplinktester.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.deeplinktester.repository.DeepLinkRepository

class DeepLinkViewModelFactory(private val repository: DeepLinkRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeepLinkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeepLinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}