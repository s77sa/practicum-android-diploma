package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.viewmodel.VacancyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModel()
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        val vacancyId = arguments?.getString(VACANCY_ID)
        val vacancy = getVacancyById(vacancyId = "91287835")

        // Заполняем макет данными из вакансии
        binding.tvJobTitle.text = vacancy?.name ?: "Unknown Vacancy"
        binding.tvSalaryLevel.text = formatSalary(vacancy?.salaryFrom, vacancy?.salaryTo, vacancy?.salaryCurrency)

        // Заполнение блока с работодателем
        vacancy?.employer?.let {
            binding.tvEmployerTitle.text = it
        }

//        val addressText = if (!vacancy?.address.isNullOrEmpty()) {
//            vacancy?.address.raw
//        } else {
//            vacancy?.area ?: "Unknown Region"
//        }
//        binding.tvEmployerCity.text = addressText
        binding.tvEmployerCity.text = vacancy?.city ?: ""

        // Загрузка логотипа работодателя в ImageView с обработкой плейсхолдера
        if (!vacancy?.employerLogoUrl.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(vacancy?.employerLogoUrl)
                .placeholder(R.drawable.ic_company_logo)
                .into(binding.ivEmployerLogo)
        } else {
            binding.ivEmployerLogo.setImageResource(R.drawable.ic_company_logo)
        }
        vacancy?.description?.let {
            binding.wvJobDescription.loadDataWithBaseURL(null, it, "text/html", "utf-8", null)
        }
        // Заполнение остальных элементов макета ...

        return binding.root
    }

    // Метод для форматирования зарплаты
    private fun formatSalary(salaryFrom: Int?, salaryTo: Int?, currency: String?): String {
        return when {
            salaryFrom != null && salaryTo != null -> "$salaryFrom - $salaryTo $currency"
            salaryFrom != null -> "от $salaryFrom $currency"
            salaryTo != null -> "до $salaryTo $currency"
            else -> "Зарплата не указана"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArgs(vacancyId: String?) = bundleOf(VACANCY_ID to vacancyId)
        private const val VACANCY_ID = "VACANCY_ID"

        // Фиктивная функция для получения вакансии по ID
        private fun getVacancyById(vacancyId: String?): Vacancy? {
            // Код ниже нужно заменить на реальную логику загрузки вакансии из БД
            return if (vacancyId != null) {
                Vacancy(
                    id = "91287835",
                    name = "Android-разработчик (удаленно)",
                    city = "Москва",
                    employer = "Hu:be",
                    employerLogoUrl = "https://hhcdn.ru/employer-logo/6076970.png",
                    department = null,
                    salaryCurrency = "RUR",
                    salaryFrom = null,
                    salaryTo = 200000,
//                    address = null,
                    contactEmail = null,
                    contactName = null,
                    contactPhones = emptyList(),
                    contactComment = null,
                    description = "https://api.hh.ru/vacancies/92139172?host=hh.ru",
                    url = "https://hh.ru/vacancy/91287835",
                    area = "Москва",
                    logo = "https://hhcdn.ru/employer-logo/6289043.jpeg",
                    experience = "От 1 года до 3 лет",
                    skills = null,
                    schedule = "Полный день",
                    isFavourite = false
                )
            } else {
                null
            }
        }
    }
}
