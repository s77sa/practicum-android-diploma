package ru.practicum.android.diploma.data.dto

data class VacancyRequest(
    val text: String,
    val area: String?,
    val page_limit: Int,
    val showSalary: Boolean,
    val industry: String?,
    val salary: Int?,
) {


    fun request(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = text
        options["per_page"] = page_limit.toString()
        if (area != null) {
            options["area"] = area
        }
        if (industry != null) {
            options["area"] = industry
        }
        if (salary != null) {
            options["salary"] = salary.toString()
        }
        options["only_with_salary"] = showSalary.toString()
        return options
    }


}


