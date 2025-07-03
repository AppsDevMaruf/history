package com.cholo.history.data

import androidx.annotation.Keep
import com.cholo.history.domain.RideHistory
import com.cholo.history.domain.RideHistoryData
import com.cholo.history.domain.RideHistoryResponse
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


@Keep
data class RideHistoryResponseDto(
    @SerializedName("title") val title: String?,
    @SerializedName("detail") val detail: String?,
    @SerializedName("data") val data: RideHistoryDataDto?
)

@Keep
data class RideHistoryDataDto(
    @SerializedName("page") val page: Int?,
    @SerializedName("size") val size: Int?,
    @SerializedName("dataCount") val dataCount: Int?,
    @SerializedName("totalCount") val totalCount: Int?,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("content") val content: List<RideHistoryDto>?
)

@Keep
data class RideHistoryDto(
    @SerializedName("rideId") val rideId: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("actualFare") val actualFare: String?,
    @SerializedName("pickup") val pickup: LatLng?,
    @SerializedName("destination") val destination: LatLng?,
    @SerializedName("pickupAddress") val pickupAddress: String?,
    @SerializedName("destinationAddress") val destinationAddress: String?,
    @SerializedName("destinationDistance") val destinationDistance: String?,
    @SerializedName("destinationTime") val destinationTime: String?,
    @SerializedName("createdAt") val createdAt: String?,
    @SerializedName("vehicle") val vehicle: String?,

    // New fields added
    @SerializedName("userName") val userName: String?,                // NEW
    @SerializedName("userPhoto") val userPhoto: String?,                // NEW
    @SerializedName("userRating") val userRating: String?,

    )


fun RideHistoryResponseDto.toDomain(): RideHistoryResponse = RideHistoryResponse(
    title = title.orEmpty(),
    detail = detail.orEmpty(),
    data = data?.toDomain() ?: RideHistoryData(0, 0, 0, 0, 0, emptyList())
)

fun RideHistoryDataDto.toDomain(): RideHistoryData = RideHistoryData(
    page = page ?: 0,
    size = size ?: 0,
    dataCount = dataCount ?: 0,
    totalCount = totalCount ?: 0,
    pageCount = pageCount ?: 0,
    content = content?.map { it.toDomain() } ?: emptyList()
)


fun RideHistoryDto.toDomain(): RideHistory = RideHistory(
    rideId = rideId.orEmpty(),
    status = status.orEmpty(),
    actualFare = actualFare.orEmpty(),
    pickup = pickup ?: LatLng(0.0, 0.0),
    destination = destination ?: LatLng(0.0, 0.0),
    pickupAddress = pickupAddress.orEmpty(),
    destinationAddress = destinationAddress.orEmpty(),
    destinationDistance = destinationDistance.orEmpty(),
    destinationTime = destinationTime.orEmpty(),
    createdAt = createdAt.orEmpty(),
    vehicle = vehicle.orEmpty(),
    // NEW fields to domain mapping
    userName = userName.orEmpty(),
    userPhoto = userPhoto,
    userRating = userRating
)
