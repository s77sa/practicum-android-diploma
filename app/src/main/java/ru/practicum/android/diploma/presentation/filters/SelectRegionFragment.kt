package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectRegionBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.presentation.filters.adapter.FilterRegionAdapter
import ru.practicum.android.diploma.presentation.filters.states.RegionSelectionState
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectRegionViewModel

class SelectRegionFragment : Fragment() {
    private var _binding: FragmentSelectRegionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectRegionViewModel by viewModel()
    private val adapter = FilterRegionAdapter {
        selectRegion(it)
    }

    private fun selectRegion(region: Area) {
        viewModel.selectRegion(region)
        findNavController().popBackStack()
    }

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRegions(null)
        binding.recyclerFilterRegion.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFilterRegion.adapter = adapter

        viewModel.regionSelectionState().observe(viewLifecycleOwner) {
            when (it) {
                RegionSelectionState.NoInternet -> {
                    setPlaceholder(PlaceholdersRegionEnum.HIDE_ALL)
                }

                RegionSelectionState.NoData -> {
                    setPlaceholder(PlaceholdersRegionEnum.SHOW_NO_REGION)
                }

                RegionSelectionState.Loading -> {
                    setPlaceholder(PlaceholdersRegionEnum.SHOW_PROGRESS_CENTER)
                }

                RegionSelectionState.Error -> {
                    setPlaceholder(PlaceholdersRegionEnum.SHOW_NO_LIST)
                }

                is RegionSelectionState.Success -> {
                    setPlaceholder(PlaceholdersRegionEnum.SHOW_RESULT)
                    adapter.regions = it.selectedRegion!!.toMutableList()
                }
            }
        }

        viewModel.getSelectedRegion()
        initListeners()
    }

    private fun initListeners() {
        binding.etSearch.addTextChangedListener(textWatcherListener())

        binding.etSearch.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    delay(SEARCH_DEBOUNCE_DELAY_MILS)

                    viewModel.searchRegionByName(binding.etSearch.text.toString())
                }
                true
            }
            false
        }

        binding.selectRegionBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setPlaceholder(placeholder: PlaceholdersRegionEnum) {
        binding.pbLoading.visibility = View.GONE
        binding.recyclerFilterRegion.visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoRegion).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoRegionList).visibility = View.GONE
        when (placeholder) {
            PlaceholdersRegionEnum.SHOW_RESULT -> {
                binding.recyclerFilterRegion.visibility = View.VISIBLE
            }

            PlaceholdersRegionEnum.SHOW_NO_REGION -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoRegion).visibility = View.VISIBLE
            }

            PlaceholdersRegionEnum.SHOW_NO_LIST -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoRegionList).visibility = View.VISIBLE
            }

            PlaceholdersRegionEnum.SHOW_PROGRESS_CENTER -> {
                binding.pbLoading.visibility = View.VISIBLE
            }

            PlaceholdersRegionEnum.HIDE_ALL -> {}
        }
    }

    private fun textWatcherListener() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!binding.etSearch.text.toString().isNullOrEmpty()) {
                binding.container.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                binding.container.endIconDrawable = requireContext().getDrawable(R.drawable.ic_close)
                if (start != before) {
                    searchJob?.cancel()
                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        delay(SEARCH_DEBOUNCE_DELAY_MILS)

                        viewModel.searchRegionByName(binding.etSearch.text.toString())
                    }
                }
            } else {
                binding.container.endIconMode = TextInputLayout.END_ICON_CUSTOM
                binding.container.endIconDrawable = requireContext().getDrawable(R.drawable.ic_search)
                val inputMethodManager =
                    requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                viewModel.getRegions("")
            }
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    companion object {
        const val VISIBLE = View.VISIBLE
        const val GONE = View.GONE
        const val SEARCH_DEBOUNCE_DELAY_MILS = 2000L
    }
}
