package com.company.fetchexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.fetchexercise.models.ListUiState
import com.company.fetchexercise.network.ApiService
import com.company.fetchexercise.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModel(
    private val apiService: ApiService = RetrofitClient.apiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val items = apiService.fetchItems()
                val valid = items.filter { !it.name.isNullOrBlank() }
                val sorted = valid.sortedWith(compareBy({ it.listId }, { it.name }))
                val grouped = sorted.groupBy { it.listId }
                _uiState.value = ListUiState.Success(grouped)
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error("Failed to load data: ${e.message}")
            }
        }
    }

    fun retryFetch() {
        _uiState.value = ListUiState.Loading
        fetchData()
    }
}

