package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Filter

class VacancyRequest(
    private val text: String,
    private val filter: Filter,
    private val page: Int?
) {
    fun map(): HashMap<String, String> {
        val options: HashMap<String, String> = HashMap()
        options["text"] = text
        options["per_page"] = filter.pageLimit.toString()
        if (filter.area != null) {
            options["area"] = filter.area
        }
        if (filter.industry != null) {
            options["area"] = filter.industry
        }
        if (filter.salary != null) {
            options["salary"] = filter.salary.toString()
        }
        if (page != null) {
            options["page"] = page.toString()
        }
        options["only_with_salary"] = filter.showSalary.toString()
        return options
    }
}
