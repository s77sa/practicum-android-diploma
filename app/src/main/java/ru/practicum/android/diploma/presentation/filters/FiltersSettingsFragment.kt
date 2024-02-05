package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersSettingsBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.viewmodel.FiltersSettingsViewModel

class FiltersSettingsFragment : Fragment() {
    private var _binding: FragmentFiltersSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltersSettingsViewModel by viewModel()
    private var country: String? = null
    private var area: String? = null

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersSettingsBinding.inflate(inflater, container, false)
        initClickListeners()
        initObservers()
        viewModel.loadData()
        return binding.root
    }

    private fun initClickListeners() {
        binding.filterSettingsHeaderBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.workplaceForward.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFiltersFragment_to_selectWorkplaceFragment)
        }

        binding.industryForward.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFiltersFragment_to_selectIndustryFragment)
        }

        binding.checkboxNoSalary.setOnClickListener {
            Log.d(TAG, "checkboxNoSalary.setOnClickListener=${binding.checkboxNoSalary.isChecked}")
            viewModel.saveSalaryCheckBox(binding.checkboxNoSalary.isChecked)
        }

        binding.salaryEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.saveExpectedSalary(s.toString())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun initObservers() {
        viewModel.plainFiltersData.observe(viewLifecycleOwner) {
            if (it != null) {
                renderCheckbox(it.notShowWithoutSalary)
                renderExpectedSalary(it.expectedSalary)
            }
        }

        viewModel.countryData.observe(viewLifecycleOwner) {
            setCountryValue(it)
        }

        viewModel.areaData.observe(viewLifecycleOwner) {
            setAreaValue(it)
        }

        viewModel.industryData.observe(viewLifecycleOwner) {
            renderIndustryTextView(it)
        }
    }

    private fun setCountryValue(value: Country?) {
        if (value != null) {
            country = value.name
        } else {
            country = "Country=null" // For test
        }
        renderWorkplaceTextView()
    }

    private fun setAreaValue(value: Area?) {
        if (value != null) {
            area = value.name
        } else {
            area = "Area=null" // For test
        }
        renderWorkplaceTextView()
    }

    private fun renderWorkplaceTextView() {
        if (country?.isNotEmpty() == true) {
            (binding.workplaceEditText as TextView).text = country
            if (area?.isNotEmpty() == true) {
                val text = "$country, $area"
                (binding.workplaceEditText as TextView).text = text
            }
        }
    }

    private fun renderIndustryTextView(value: Industry?) {
        if (value != null) {
            (binding.industryEditText as TextView).text = value.name
        } else {
            (binding.industryEditText as TextView).text = "Industry=null" // For test
        }
    }

    private fun renderExpectedSalary(salary: Int) {
        val oldValue = (binding.salaryEditText as TextView).text.toString()
        if (salary > 0 && oldValue != salary.toString()) {
            (binding.salaryEditText as TextView).text = salary.toString()
        }
    }

    private fun renderCheckbox(isChecked: Boolean) {
        binding.checkboxNoSalary.isChecked = isChecked
    }

    companion object {
        private val TAG = FiltersSettingsFragment::class.java.simpleName
    }
}
