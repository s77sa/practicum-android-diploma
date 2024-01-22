package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlDto(
    @SerializedName("90")
    val smallSize: String,
    @SerializedName("240")
    val largeSize: String,
    @SerializedName("original")
    val originalSize: String,
)
