package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectWorkplaceBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.states.WorkplaceSelectionState
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectWorkplaceViewModel

class SelectWorkplaceFragment : Fragment() {
    private var _binding: FragmentSelectWorkplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectWorkplaceViewModel by viewModel()
    private var isCountryButtonVisible = false
    private var isRegionButtonVisible = false
    private var country: Country? = null
    private var region: Area? = null
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

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
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
        viewModel.countryData.observe(viewLifecycleOwner) {
            renderCountryTextView(it)
        }
    }

    private fun renderCountryTextView(country: Country?) {
        if (country != null) {
            (binding.countryEditText as TextView).text = country.name
        }
    }

    private fun handleRegionSelectionState(state: WorkplaceSelectionState) {
        when (state) {
            is WorkplaceSelectionState.Empty -> {
                binding.clearRegion.visibility = View.GONE
            }

            is WorkplaceSelectionState.RegionFilled -> {
                region = Area(
                    state.region.id,
                    state.region.name,
                    null,
                    null
                )
                binding.clearRegion.visibility = View.VISIBLE
                binding.selectRegionButton.isVisible = false
                binding.regionEditText.setText(state.region.name)
                binding.filterSettingsApply.isVisible = true
                if (country == null) {
                    binding.countryEditText.setText(state.region.countryName)
                    binding.clearCountryName.visibility = View.VISIBLE
                    country = Country(
                        id = state.region.countryId,
                        name = state.region.countryName
                    )
                }
                updateButtonsVisibility()
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
                country = state.country
                binding.clearCountryName.visibility = View.VISIBLE
                binding.selectCountryButton.isVisible = false
                binding.countryEditText.setText(state.country.name)
                binding.filterSettingsApply.isVisible = true
            }

            else -> {}
        }
    }

    private fun initClickListeners() {
        binding.selectWorkplaceBack.setOnClickListener {
            viewModel.clearSelectedRegion()
            viewModel.clearSelectedCountry()
            findNavController().popBackStack()
        }
        binding.selectCountryButton.setOnClickListener {
            navigateToCountrySelection()
        }

        binding.selectRegionButton.setOnClickListener {
            navigateToRegionSelection()
        }
        binding.clearCountryName.setOnClickListener {
            clearCountryField()
            clearRegionField()
        }

        binding.clearRegion.setOnClickListener {
            clearRegionField()
        }
        binding.filterSettingsApply.setOnClickListener {
            navigateToFiltersSettingsFragment()
        }

        binding.countryEditText.setOnClickListener {
            navigateToCountrySelection()
        }

        binding.regionEditText.setOnClickListener {
            navigateToRegionSelection()
        }
    }

    private fun navigateToRegionSelection() {
        var countryId = ""
        if (country != null) {
            countryId = country!!.id
        }
        findNavController().navigate(
            R.id.action_selectWorkplaceFragment_to_selectRegionFragment,
            SelectRegionFragment.createArgs(id = countryId)
        )
    }

    private fun navigateToCountrySelection() {
        findNavController().navigate(R.id.action_selectWorkplaceFragment_to_selectCountryFragment)
    }

    private fun navigateToFiltersSettingsFragment() {
        findNavController().popBackStack()
        viewModel.saveCountry(country)
        viewModel.saveRegion(region)
    }

    private fun clearRegionField() {
        region = null
        isRegionButtonVisible = false
        binding.regionEditText.text = null
        viewModel.clearSelectedRegion()
        updateButtonsVisibility()
    }

    private fun clearCountryField() {
        country = null
        isCountryButtonVisible = false
        binding.countryEditText.text = null
        viewModel.clearSelectedCountry()
        updateButtonsVisibility()
    }

    private fun updateButtonsVisibility() {
        val selectedCountry = country
        val isCountryFieldEmpty = binding.countryEditText.text.isNullOrEmpty()
        binding.clearCountryName.isVisible = selectedCountry != null && !isCountryFieldEmpty
        binding.selectCountryButton.isVisible = selectedCountry == null || isCountryFieldEmpty

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
