package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSelectCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.adapter.FilterCountryAdapter
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectCountryViewModel

class SelectCountryFragment : Fragment() {
    private var _binding: FragmentSelectCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectCountryViewModel by viewModel()
    private var countryAdapter: FilterCountryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSelectCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecyclerView()
        observeCountryList()
    }

    private fun observeCountryList() {
        lifecycleScope.launch {
            viewModel.getCountries()
        }

        viewModel.countrySelectionState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                pbLoading.isVisible = when (state) {
                    is CountrySelectionState.Loading -> true
                    is CountrySelectionState.Success -> {
                        lifecycleScope.launch {
                            val countries = state.selectedCountry
                            val countriesList = countries.map { area ->
                                Country(area.id, area.name)
                            }
                            countryAdapter?.countries = countriesList.toMutableList()
                            countryAdapter?.notifyDataSetChanged()
                        }
                        false
                    }

                    is CountrySelectionState.ServerIssue,
                    is CountrySelectionState.NoData -> false
                }

                tvError.isVisible = state is CountrySelectionState.ServerIssue || state is CountrySelectionState.NoData
                ivError.isVisible = state is CountrySelectionState.ServerIssue || state is CountrySelectionState.NoData
                recyclerFilterCountry.isVisible = state is CountrySelectionState.Success
            }
        }
    }

    private fun initRecyclerView() {
        countryAdapter = FilterCountryAdapter { country ->
            selectCountry(country)
        }
        binding.recyclerFilterCountry.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }
    }

    private fun initListeners() {
        binding.selectCountryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun selectCountry(country: Country) {
        viewModel.applyCountryFilter(country)
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SELECTED_COUNTRY = "selectedCountry"
    }
}
