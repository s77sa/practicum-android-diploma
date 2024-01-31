package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.util.SalaryUtils
import ru.practicum.android.diploma.presentation.vacancy.models.VacancyScreenState
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.VacancyViewModel
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.getColorHexString
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.getHMLDescription

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyViewModel by viewModel()
    private var vacancyId: String? = null
    private var isFavourite: Boolean = false
    private var currentVacancy: Vacancy? = null

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        vacancyId = arguments?.getString(VACANCY_ID)
        val backgroundColorRes = R.color.whiteDayBlackNight
        val backgroundColor = ContextCompat.getColor(requireContext(), backgroundColorRes)
        binding.wvJobDescription.setBackgroundColor(backgroundColor)
        binding.wvKeySkills.setBackgroundColor(backgroundColor)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val errorPlaceholder: LinearLayout = view.findViewById(R.id.ll_error_field)
        val vacancyDescriptionView: NestedScrollView = view.findViewById(R.id.nsv_vacancy_description)

        viewModel.vacancyScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyScreenState.Success -> {
                    val vacancy = state.vacancy
                    updateUIWithVacancyDetails(vacancy)
                    showLoadingIndicator(false, progressBar)
                    showErrorPlaceholder(false, errorPlaceholder)
                    vacancyDescriptionView.isVisible = true
                }

                is VacancyScreenState.Error -> {
                    showLoadingIndicator(false, progressBar)
                    showErrorPlaceholder(true, errorPlaceholder)
                    vacancyDescriptionView.isVisible = false
                }

                is VacancyScreenState.Loading -> {
                    showLoadingIndicator(true, progressBar)
                    showErrorPlaceholder(false, errorPlaceholder)
                    vacancyDescriptionView.isVisible = false
                }
            }
        }

        vacancyId?.let { viewModel.getVacancyDetailsById(it) }
        viewModel.isFavourite.observe(viewLifecycleOwner) { isFavourite ->
            this.isFavourite = isFavourite
            updateFavouriteIcon()
        }
        initClickListeners()
    }

    private fun updateFavouriteIcon() {
        if (isFavourite) {
            binding.ivDetailsFavouriteButton.setImageResource(R.drawable.ic_favorites_checked)
        } else {
            binding.ivDetailsFavouriteButton.setImageResource(R.drawable.ic_favorites_unchecked)
        }
    }

    private fun showErrorPlaceholder(show: Boolean, errorPlaceholder: LinearLayout) {
        errorPlaceholder.isVisible = show
    }

    private fun showLoadingIndicator(show: Boolean, progressBar: ProgressBar) {
        progressBar.isVisible = show
    }

    private fun updateUIWithVacancyDetails(vacancy: Vacancy?) {
        if (vacancy != null) {
            currentVacancy = vacancy

            fillJobDetails(vacancy)
            fillEmployerDetails(vacancy)
            loadEmployerLogo(vacancy)
            fillLocationDetails(vacancy)
            fillExperienceAndEmploymentDetails(vacancy)
            loadJobDescription(vacancy)
            fillKeySkillsDetails(vacancy)
            showContactsIfRequired(vacancy)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.checkFavouriteStatus(vacancy.id)
            }
        }
    }

    private fun fillJobDetails(vacancy: Vacancy) {
        binding.tvJobTitle.text = vacancy.name
        binding.tvSalaryLevel.text = SalaryUtils.formatSalary(
            requireContext(),
            vacancy.salaryFrom,
            vacancy.salaryTo,
            vacancy.salaryCurrency
        )
    }

    private fun fillEmployerDetails(vacancy: Vacancy) {
        vacancy.employer?.let {
            binding.tvEmployerTitle.text = it
        }
    }

    private fun loadEmployerLogo(vacancy: Vacancy) {
        if (!vacancy.employerLogoUrl.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load(vacancy.employerLogoUrl)
                .placeholder(R.drawable.ic_company_logo)
                .transform(
                    RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_12dp))
                )
                .into(binding.ivEmployerLogo)
        } else {
            binding.ivEmployerLogo.setImageResource(R.drawable.ic_company_logo)
        }
    }

    private fun fillLocationDetails(vacancy: Vacancy) {
        val employerCityText = if (!vacancy.address.isNullOrEmpty()) {
            vacancy.address
        } else {
            vacancy.area
        }
        binding.tvEmployerCity.text = employerCityText
    }

    private fun fillExperienceAndEmploymentDetails(vacancy: Vacancy) {
        binding.tvActualExperience.text = vacancy.experience ?: ""
        binding.tvEmploymentAndRemote.text = vacancy.schedule ?: ""
    }

    private fun loadJobDescription(vacancy: Vacancy) {
        val jobDescriptionHtml = currentVacancy?.description?.let { getHMLDescription(vacancy, it, requireContext()) }
        if (jobDescriptionHtml != null) {
            binding.wvJobDescription.loadDataWithBaseURL(null, jobDescriptionHtml, "text/html", "utf-8", null)
        }
    }

    private fun fillKeySkillsDetails(vacancy: Vacancy) {
        if (!vacancy.skills.isNullOrEmpty()) {
            binding.tvKeySkillsTitle.text = getString(R.string.key_skills)
            binding.wvKeySkills.loadDataWithBaseURL(
                null,
                formatSkillsList(vacancy.skills),
                "text/html",
                "utf-8",
                null
            )
        } else {
            binding.tvKeySkillsTitle.isVisible = false
            binding.wvKeySkills.isVisible = false
        }
    }

    private fun showContactsIfRequired(vacancy: Vacancy) {
        if (shouldShowContacts(vacancy)) {
            binding.clContactsContainer.isVisible = true
            fillContactDetails(vacancy)
        } else {
            binding.clContactsContainer.isVisible = false
        }
    }

    private fun fillContactDetails(vacancy: Vacancy) {
        binding.tvContactNameField.text = vacancy.contactName ?: ""

        binding.tvEmailField.text = vacancy.contactEmail ?: ""
        binding.tvEmailTitle.isVisible = !vacancy.contactEmail.isNullOrEmpty()

        binding.tvCommentField.text = formatContactsComments(vacancy.contactComment)
        binding.tvCommentTitle.isVisible = !vacancy.contactComment.isNullOrEmpty()

        binding.tvTelephoneField.isVisible = vacancy.contactPhones.isNotEmpty()
        if (vacancy.contactPhones.isNotEmpty()) {
            binding.tvTelephoneField.text = vacancy.contactPhones[0]
        }

        setOnClickListenersForContacts()
    }

    private fun setOnClickListenersForContacts() {
        binding.tvEmailField.setOnClickListener {
            viewModel.sendEmail(
                binding.tvEmailField.text.toString(),
                "",
                ""
            )
        }

        binding.tvTelephoneField.setOnClickListener {
            viewModel.makeCall(binding.tvTelephoneField.text.toString())
        }
    }

    private fun shouldShowContacts(vacancy: Vacancy): Boolean {
        return vacancy.contactPhones.isNotEmpty() ||
            !vacancy.contactEmail.isNullOrEmpty() ||
            !vacancy.contactComment.isNullOrEmpty() ||
            !vacancy.contactName.isNullOrEmpty()
    }

    private fun formatContactsComments(contacts: List<String?>?): String {
        return contacts?.joinToString("\n") ?: ""
    }

    private fun formatSkillsList(skills: List<String>): String {
        val bulletPoint = "&#8226; " // HTML-код для кружочка
        return skills.joinToString("<br/>") {
            "<span style=\"color: ${getColorHexString(requireContext())};\">$bulletPoint$it</span>"
        }
    }

    private fun initClickListeners() {
        binding.ivDetailsBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivDetailsShareButton.setOnClickListener {
            viewModel.shareVacancy(vacancyId)
        }
        binding.ivDetailsFavouriteButton.setOnClickListener {
            currentVacancy?.let {
                viewModel.toggleFavouriteStatus(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        internal const val VACANCY_ID = "VACANCY_ID"
    }
}
