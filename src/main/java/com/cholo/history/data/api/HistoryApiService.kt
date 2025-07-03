package com.cholo.history.data.api

import com.chalo.core.model.base.ApiResponse
import com.cholo.history.data.RideHistoryDto
import com.cholo.history.domain.model.RideLastLocationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryApiService {
    @POST("api/rides/history")
    suspend fun rideHistory(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Body request: RideLastLocationRequest
    ): Response<ApiResponse<List<RideHistoryDto>>>
}
