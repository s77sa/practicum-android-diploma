package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val id: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrlDto?,
    val name: String,
    val url: String
)
