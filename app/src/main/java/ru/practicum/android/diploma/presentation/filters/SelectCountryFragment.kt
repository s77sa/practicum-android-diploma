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
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSelectCountryBinding
import ru.practicum.android.diploma.presentation.filters.adapter.FilterCountryAdapter
import ru.practicum.android.diploma.presentation.filters.states.CountrySelectionState
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectCountryViewModel
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

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
            when (state) {
                is CountrySelectionState.Loading -> {
                    binding.pbLoading.isVisible = true
                    binding.tvError.isVisible = false
                    binding.ivError.isVisible = false
                    binding.recyclerFilterCountry.isVisible = false
                }
                is CountrySelectionState.Success -> {
                    lifecycleScope.launch {
                        val countries = state.selectedCountry
                        countries.let {
                            // Обновление списка стран в адаптере
                            val countriesList: MutableList<Country> = it.map { area ->
                                Country(area.id, area.name)
                            }.toMutableList()
                            countryAdapter?.countries = countriesList
                            countryAdapter?.notifyDataSetChanged()
                        }
                        binding.pbLoading.isVisible = false
                        binding.tvError.isVisible = false
                        binding.ivError.isVisible = false
                        binding.recyclerFilterCountry.isVisible = true
                    }
                }
                is CountrySelectionState.ServerIssue -> {
                    binding.pbLoading.isVisible = false
                    binding.tvError.isVisible = true
                    binding.ivError.isVisible = true
                    binding.recyclerFilterCountry.isVisible = false
                }
                is CountrySelectionState.NoData -> {
                    binding.pbLoading.isVisible = false
                    binding.tvError.isVisible = true
                    binding.ivError.isVisible = true
                    binding.recyclerFilterCountry.isVisible = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        countryAdapter = FilterCountryAdapter { country ->
            viewModel.applyCountryFilter(country)
            navigateToSelectWorkplaceFragment(country.name)
        }
        binding.recyclerFilterCountry.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }
    }

    private fun navigateToSelectWorkplaceFragment(selectedCountry: String) {
        val bundle = Bundle()
        bundle.putString(SELECTED_COUNTRY, selectedCountry)
        findNavController().navigate(R.id.action_selectCountryFragment_to_selectWorkplaceFragment, bundle)
    }
    private fun initListeners() {
        binding.selectCountryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val SELECTED_COUNTRY = "selectedCountry"
    }
}
