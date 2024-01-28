package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
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
        vacancyId = arguments?.getString(VACANCY_ID)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val errorPlaceholder: LinearLayout = view.findViewById(R.id.ll_error_pole)
        val vacancyDescriptionView: NestedScrollView = view.findViewById(R.id.nsv_vacancy_description)

        viewModel.vacancyScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyScreenState.Success -> {
                    val vacancy = state.vacancy
                    updateUIWithVacancyDetails(vacancy)
                    showLoadingIndicator(false, progressBar)
                    showErrorPlaceholder(false, errorPlaceholder)
                    vacancyDescriptionView.visibility = View.VISIBLE
                }

                is VacancyScreenState.Error -> {
                    showLoadingIndicator(false, progressBar)
                    showErrorPlaceholder(true, errorPlaceholder)
                    vacancyDescriptionView.visibility = View.GONE
                }

                is VacancyScreenState.Loading -> {
                    showLoadingIndicator(true, progressBar)
                    showErrorPlaceholder(false, errorPlaceholder)
                    vacancyDescriptionView.visibility = View.GONE
                }
            }
        }

        vacancyId?.let { viewModel.getVacancyDetailsById(it) }

        initClickListeners()
    }

    private fun showErrorPlaceholder(show: Boolean, errorPlaceholder: LinearLayout) {
        errorPlaceholder.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showLoadingIndicator(show: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateUIWithVacancyDetails(vacancy: Vacancy?) {
        if (vacancy != null) {
            // Заполняем макет данными из вакансии
            binding.tvJobTitle.text = vacancy.name
            binding.tvSalaryLevel.text = formatSalary(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrency)

            // Заполнение блока с работодателем
            vacancy.employer?.let {
                binding.tvEmployerTitle.text = it
            }

            // Загрузка логотипа работодателя в ImageView с обработкой плейсхолдера
            if (!vacancy.employerLogoUrl.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(vacancy.employerLogoUrl)
                    .placeholder(R.drawable.ic_company_logo)
                    .into(binding.ivEmployerLogo)
            } else {
                binding.ivEmployerLogo.setImageResource(R.drawable.ic_company_logo)
            }

            // Заполнение карточки работодателя
            binding.tvEmployerTitle.text = vacancy.employer ?: ""

            // Используем адрес, если он есть, в противном случае area
            val employerCityText = if (!vacancy.address.isNullOrEmpty()) {
                vacancy.address
            } else {
                vacancy.area
            }
            binding.tvEmployerCity.text = employerCityText

            // Заполнение опыта работы и условий труда
            binding.tvActualExperience.text = vacancy.experience ?: ""
            binding.tvEmploymentAndRemote.text = vacancy.schedule ?: ""

            // Загрузка описания вакансии в WebView
            vacancy.description.let {
                binding.wvJobDescription.loadDataWithBaseURL(null, it, "text/html", "utf-8", null)
            }
        }
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

    }
}
