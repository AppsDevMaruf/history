package com.cholo.history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalo.core.model.base.ApiResult
import com.cholo.history.domain.GetRideHistoryUseCase
import com.cholo.history.domain.RideHistory
import com.cholo.history.domain.model.RideLastLocationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HistoryViewModel ( private val getRideHistoryUseCase: GetRideHistoryUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()

    // ********************************rideHistory****************************************************
    fun rideHistory(rideLastLocationRequest: RideLastLocationRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getRideHistoryUseCase(rideLastLocationRequest)) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            rideHistory= result.data,
                            errorMessage = null
                        )
                    }
                }
                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
}

data class HistoryUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val rideHistory: List<RideHistory>? = null,
)
