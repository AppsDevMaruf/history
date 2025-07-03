package com.cholo.history.domain

import com.chalo.core.model.base.ApiResult
import com.cholo.history.data.toDomain
import com.cholo.history.domain.model.RideLastLocationRequest

class GetRideHistoryUseCase(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(rideLastLocationRequest: RideLastLocationRequest): ApiResult<List<RideHistory>?> {
        // Delegate to repository and map the result
        return when (val result = repository.rideHistory(rideLastLocationRequest)) {
            is ApiResult.Success -> {
                ApiResult.Success(result.data?.map { it.toDomain() })
            }
            is ApiResult.Error -> {
                ApiResult.Error(result.message)
            }
        }
    }
}
