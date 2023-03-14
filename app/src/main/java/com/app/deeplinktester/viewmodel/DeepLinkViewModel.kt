package com.app.deeplinktester.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.deeplinktester.repository.DeepLinkRepository
import com.app.deeplinktester.room.model.DeepLinkData
import kotlinx.coroutines.launch

class DeepLinkViewModel(private val repository: DeepLinkRepository) : ViewModel() {

    val allDeepLinks: LiveData<List<DeepLinkData>> = repository.allDeepLinks.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(deepLinkData: DeepLinkData) = viewModelScope.launch {
        repository.insert(deepLinkData)
    }

    fun delete(deeplink: String) = viewModelScope.launch {
        repository.delete(deeplink)
    }
}