package com.cholo.history.domain

import com.chalo.core.model.base.ApiResult
import com.cholo.history.data.RideHistoryDto
import com.cholo.history.domain.model.RideLastLocationRequest

// RideRepository.kt
interface HistoryRepository {
    suspend  fun rideHistory(request: RideLastLocationRequest): ApiResult<List<RideHistoryDto>?>
}
