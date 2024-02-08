package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.R.id.action_selectWorkplaceFragment_to_settingsFiltersFragment
import ru.practicum.android.diploma.databinding.FragmentSelectWorkplaceBinding
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectWorkplaceViewModel

class SelectWorkplaceFragment : Fragment() {
    private var _binding: FragmentSelectWorkplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectWorkplaceViewModel by viewModel()
    private var isCountryButtonVisible = false
    private var isRegionButtonVisible = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSelectWorkplaceBinding.inflate(inflater, container, false)
        arguments?.getString(SELECTED_COUNTRY)?.let { selectedCountry ->
            binding.countryEditText.setText(selectedCountry)
        }
        initClickListeners()
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectRegionButton.isVisible = isRegionButtonVisible
        binding.clearRegion.isVisible = isRegionButtonVisible
        updateButtonsVisibility()
    }

    private fun observeViewModel() {
        viewModel.countrySelectionState.observe(viewLifecycleOwner) { state ->
            handleCountrySelectionState(state)
        }

        viewModel.regionSelectionState.observe(viewLifecycleOwner) { state ->
            handleRegionSelectionState(state)
        }
    }

    private fun handleRegionSelectionState(state: WorkplaceSelectionState) {
        when (state) {
            is WorkplaceSelectionState.Empty -> {
                binding.clearRegion.visibility = View.GONE
            }

            is WorkplaceSelectionState.RegionFilled -> {
                binding.clearRegion.visibility = View.VISIBLE
            }

            else -> {}
        }
    }

    private fun handleCountrySelectionState(state: WorkplaceSelectionState) {
        when (state) {
            is WorkplaceSelectionState.Empty -> {
                binding.clearCountryName.visibility = View.GONE
            }

            is WorkplaceSelectionState.CountryFilled -> {
                binding.clearCountryName.visibility = View.VISIBLE
            }

            else -> {}
        }
    }

    private fun initClickListeners() {
        binding.selectWorkplaceBack.setOnClickListener {
            findNavController().navigate(action_selectWorkplaceFragment_to_settingsFiltersFragment)
        }
        binding.selectCountryBottom.setOnClickListener {
            findNavController().navigate(R.id.action_selectWorkplaceFragment_to_selectCountryFragment)
        }

        binding.selectRegionButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_selectWorkplaceFragment_to_selectRegionFragment,
                SelectRegionFragment.createArgs(id = "")
            )
        }
        binding.clearCountryName.setOnClickListener {
            clearCountryField()
        }

        binding.clearRegion.setOnClickListener {
            clearRegionField()
        }
        binding.filterSettingsApply.setOnClickListener {
            navigateToFiltersSettingsFragment()
        }
    }

    private fun navigateToFiltersSettingsFragment() {
        val selectedCountry = binding.countryEditText.text.toString()
        val selectedRegion = binding.regionEditText.text.toString()
        val bundle = bundleOf(
            SELECTED_COUNTRY to selectedCountry,
            SELECTED_REGION to selectedRegion
        )
        findNavController().navigate(
            action_selectWorkplaceFragment_to_settingsFiltersFragment,
            bundle
        )
    }

    private fun clearRegionField() {
        isRegionButtonVisible = false
        binding.regionEditText.text = null
        updateButtonsVisibility()
    }

    private fun clearCountryField() {
        isCountryButtonVisible = false
        binding.countryEditText.text = null
        updateButtonsVisibility()
    }

    private fun updateButtonsVisibility() {
        val selectedCountry = arguments?.getString(SELECTED_COUNTRY)
        val isCountryFieldEmpty = binding.countryEditText.text.isNullOrEmpty()
        binding.clearCountryName.isVisible = !selectedCountry.isNullOrEmpty() && !isCountryFieldEmpty
        binding.selectCountryBottom.isVisible = selectedCountry.isNullOrEmpty() || isCountryFieldEmpty

        val isRegionFieldEmpty = binding.regionEditText.text.isNullOrEmpty()
        binding.clearRegion.isVisible = !isRegionFieldEmpty
        binding.selectRegionButton.isVisible = isRegionFieldEmpty

        binding.filterSettingsApply.isVisible = !isCountryFieldEmpty
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SELECTED_COUNTRY = "selectedCountry"
        const val SELECTED_REGION = "selectedRegion"
    }
}
