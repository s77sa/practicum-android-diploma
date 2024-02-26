package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class MetroDto(
    @SerializedName("line_name")
    val lineName: String?,
    val lat: String?,
    val lng: String?,
    @SerializedName("station_name")
    val stationName: String?,
)
