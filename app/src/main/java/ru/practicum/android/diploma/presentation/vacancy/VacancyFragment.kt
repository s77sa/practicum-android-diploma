package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private var vacancyId: String? = null
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
//        vacancyId = arguments?.getString(VACANCY_ID)
        vacancyId = "91287835"
        val vacancy = getVacancyById(vacancyId)

        // Заполняем макет данными из вакансии
        binding.tvJobTitle.text = vacancy?.name ?: ""
        Log.d("Проверка", "$vacancy")
        binding.tvSalaryLevel.text = formatSalary(vacancy?.salaryFrom, vacancy?.salaryTo, vacancy?.salaryCurrency)

        // Заполнение блока с работодателем
        vacancy?.employer?.let {
            binding.tvEmployerTitle.text = it
        }

        val addressText = if (!vacancy?.address.isNullOrEmpty()) {
            vacancy?.address
        } else {
            vacancy?.area ?: ""
        }
        binding.tvEmployerCity.text = addressText

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.ivDetailsBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivDetailsShareButton.setOnClickListener {
            viewModel.shareVacancy(vacancyId)
        }
    }

    // Метод для форматирования уровня зарплаты
    private fun formatSalary(salaryFrom: Int?, salaryTo: Int?, currency: String?): String {
        val fromText = getString(R.string.from)
        val toText = getString(R.string.to)
        return when {
            salaryFrom != null && salaryTo != null -> "$salaryFrom - $salaryTo $currency"
            salaryFrom != null -> "$fromText $salaryFrom $currency"
            salaryTo != null -> "$toText $salaryTo $currency"
            else -> getString(R.string.salary_not_specified)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createArgs(vacancyId: String?) = bundleOf(VACANCY_ID to vacancyId)
        internal const val VACANCY_ID = "VACANCY_ID"

        // Фиктивная функция для получения вакансии по ID
        private fun getVacancyById(vacancyId: String?): Vacancy? {
            // Код ниже нужно заменить на реальную логику загрузки вакансии из БД
            return if (vacancyId != null) {
                Vacancy(
                    id = vacancyId,
                    name = "Android-разработчик (удаленно)",
                    city = "Москва",
                    employer = "Hu:be",
                    employerLogoUrl = "https://hhcdn.ru/employer-logo/6076970.png",
                    department = null,
                    salaryCurrency = "RUR",
                    salaryFrom = null,
                    salaryTo = 200000,
                    address = "Москва, Пресненская набережная, 10",
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
