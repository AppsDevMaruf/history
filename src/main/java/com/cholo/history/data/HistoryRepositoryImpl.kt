package com.cholo.history.data

import com.chalo.core.model.base.ApiResult
import com.chalo.core.model.base.safeApiCall
import com.cholo.history.data.api.HistoryApiService
import com.cholo.history.domain.HistoryRepository
import com.cholo.history.domain.model.RideLastLocationRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class HistoryRepositoryImpl(
    private val apiService: HistoryApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistoryRepository {
    override suspend fun rideHistory(request: RideLastLocationRequest): ApiResult<List<RideHistoryDto>?> {
        return dispatcher.safeApiCall {
            apiService.rideHistory(request = request)
        }
    }

}
