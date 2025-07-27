package com.company.fetchexercise.models

sealed class ListUiState {
    object Loading : ListUiState()
    data class Success(val groupedItems: Map<Int, List<Item>>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}