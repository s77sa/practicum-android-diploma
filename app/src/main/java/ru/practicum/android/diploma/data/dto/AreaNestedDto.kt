package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaNestedDto(
    val id: String,
    val name: String,
    val areas: List<AreaNestedDto>?,
    @SerializedName("parent_id")
    val parentId: String?,
) : Response()
