package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.Response

data class IndustriesResponse(val items: ArrayList<IndustryDto>) : Response()
