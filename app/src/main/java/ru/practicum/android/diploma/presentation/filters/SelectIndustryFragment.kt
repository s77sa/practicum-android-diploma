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
import ru.practicum.android.diploma.databinding.FragmentSelectIndustryBinding
import ru.practicum.android.diploma.domain.models.FilterIndustryStates
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.presentation.filters.adapter.FilterIndustryAdapter
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectIndustryViewModel

class SelectIndustryFragment : Fragment(R.layout.fragment_select_industry) {
    private var _binding: FragmentSelectIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectIndustryViewModel by viewModel()
    private val adapter = FilterIndustryAdapter {
        chooseIndustry(it)
    }
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getState().observe(viewLifecycleOwner) {initStates(it)}
        viewModel.getIndustries()
        initListeners()
        initAdapter()
    }

    fun initStates (data: FilterIndustryStates){
        when (data) {
            FilterIndustryStates.ConnectionError -> {
                binding.recyclerFilterIndustry.visibility = GONE
                binding.pbLoading.visibility = GONE
                binding.tvError.visibility = VISIBLE
                binding.ivError.visibility = VISIBLE
                binding.tvError.setText(R.string.no_internet)
                binding.ivError.setImageResource(R.drawable.il_scull)
            }

            FilterIndustryStates.Loading -> {
                binding.pbLoading.visibility = VISIBLE
            }

            FilterIndustryStates.ServerError -> {
                binding.recyclerFilterIndustry.visibility = GONE
                binding.pbLoading.visibility = GONE
                binding.tvError.visibility = VISIBLE
                binding.ivError.visibility = VISIBLE
                binding.tvError.setText(R.string.server_error)
                binding.ivError.setImageResource(R.drawable.il_3_server_cry)
            }

            is FilterIndustryStates.Success -> {
                binding.recyclerFilterIndustry.visibility = VISIBLE
                binding.pbLoading.visibility = GONE
                adapter.industries.clear()
                adapter.industries = data.industries.toMutableList()
                adapter.notifyDataSetChanged()
                binding.filterSettingsApply.visibility = GONE
                viewModel.isChecked()
            }

            FilterIndustryStates.HasSelected -> {
                binding.filterSettingsApply.visibility = VISIBLE
                binding.pbLoading.visibility = GONE
            }

            FilterIndustryStates.Empty -> {
                binding.recyclerFilterIndustry.visibility = GONE
                binding.pbLoading.visibility = GONE
                binding.filterSettingsApply.visibility = GONE
                binding.tvError.visibility = VISIBLE
                binding.ivError.visibility = VISIBLE
                binding.tvError.setText(R.string.no_such_industry)
                binding.ivError.setImageResource(R.drawable.il_angry_cat)
            }
        }
    }


    private fun initListeners() {
        binding.etSearch.addTextChangedListener(textWatcherListener())

        binding.etSearch.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    delay(SEARCH_DEBOUNCE_DELAY_MILS)

                    viewModel.getIndustriesByName(binding.etSearch.text.toString())
                }
                true
            }
            false
        }

        binding.filterSettingsApply.setOnClickListener {
            viewModel.saveIndustryFilter()
            findNavController().popBackStack()
        }

        binding.selectIndustryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
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

                        viewModel.getIndustriesByName(binding.etSearch.text.toString())
                    }
                }
            } else {
                binding.container.endIconMode = TextInputLayout.END_ICON_CUSTOM
                binding.container.endIconDrawable = requireContext().getDrawable(R.drawable.ic_search)
                val view = requireActivity().currentFocus
                if (view != null) {
                    val inputMethodManager =
                        requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    view.clearFocus()
                }
                viewModel.getIndustries()
            }
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    private fun chooseIndustry(industry: Industry) {
        viewModel.bufferIndustry(industry)
    }

    private fun initAdapter() {
        binding.recyclerFilterIndustry.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFilterIndustry.adapter = adapter
    }

    companion object {
        const val VISIBLE = View.VISIBLE
        const val GONE = View.GONE
        const val SEARCH_DEBOUNCE_DELAY_MILS = 2000L
    }
}
