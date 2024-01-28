package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    val building: String?,
    val city: String?,
    val lat: String?,
    val lng: String?,
    val street: String?,
    val description: String?,
    val raw: String,
    @SerializedName("metro_stations")
    val metroStations: List<MetroDto>,
)
