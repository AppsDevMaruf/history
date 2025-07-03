package com.cholo.history.domain

import com.google.android.gms.maps.model.LatLng

data class RideHistoryResponse(
    val title: String,
    val detail: String,
    val data: RideHistoryData
)

data class RideHistoryData(
    val page: Int,
    val size: Int,
    val dataCount: Int,
    val totalCount: Int,
    val pageCount: Int,
    val content: List<RideHistory>
)

data class RideHistory(
    val rideId: String,
    val status: String,
    val actualFare: String,
    val pickup: LatLng,
    val destination: LatLng,
    val pickupAddress: String,
    val destinationAddress: String,
    val destinationDistance: String,
    val destinationTime: String,
    val createdAt: String,
    val vehicle: String,

    // NEW FIELDS
    val userName: String?,
    val userPhoto: String?,
    val userRating: String?,
)
