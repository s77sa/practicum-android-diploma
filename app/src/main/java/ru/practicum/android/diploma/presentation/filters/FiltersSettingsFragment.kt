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
import ru.practicum.android.diploma.presentation.filters.viewmodel.FiltersSettingsViewModel

class FiltersSettingsFragment : Fragment() {
    private var _binding: FragmentFiltersSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltersSettingsViewModel by viewModel()

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
