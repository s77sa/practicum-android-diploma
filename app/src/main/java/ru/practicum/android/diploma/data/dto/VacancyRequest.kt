package ru.practicum.android.diploma.data.dto

class VacancyRequest(
    private val text: String,
    private val area: String?,
    private val pageLimit: Int = 20,
    private val showSalary: Boolean,
    private val industry: String?,
    private val salary: Int?,
    private val page: Int?,
) {
    fun map(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = text
        options["per_page"] = pageLimit.toString()
        if (area != null) {
            options["area"] = area
        }
        if (industry != null) {
            options["area"] = industry
        }
        if (salary != null) {
            options["salary"] = salary.toString()
        }
        if (page != null) {
            options["page"] = page.toString()
        }
        options["only_with_salary"] = showSalary.toString()
        return options
    }
}
