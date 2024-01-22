package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaResponse(
    val id: String,
    val name: String,
    val areas: List<AreaResponse>?,
    @SerializedName("parent_id")
    val parentId: String?,
) : Response()
