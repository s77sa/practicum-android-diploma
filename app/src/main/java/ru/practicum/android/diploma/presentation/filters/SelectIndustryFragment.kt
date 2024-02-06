package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    private var foundIndustries: List<Industry>? = null
    private var selectedIndustry: Industry? = null
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
        viewModel.getState().observe(viewLifecycleOwner) {
            Log.i("Industry", "State is $it")
            initStates(it)
        }
        viewModel.getIndustries()
        initListeners()
        initAdapter()
    }

    fun initStates(data: FilterIndustryStates) {
        when (data) {
            FilterIndustryStates.ConnectionError -> {
                hideViewOnNoSuccess()
                binding.tvError.setText(R.string.no_internet)
                binding.ivError.setImageResource(R.drawable.il_scull)
            }

            FilterIndustryStates.Loading -> {
                binding.pbLoading.visibility = VISIBLE
            }

            FilterIndustryStates.ServerError -> {
                hideViewOnNoSuccess()
                binding.tvError.setText(R.string.server_error)
                binding.ivError.setImageResource(R.drawable.il_3_server_cry)
            }

            is FilterIndustryStates.Success -> {
                binding.recyclerFilterIndustry.visibility = VISIBLE
                binding.pbLoading.visibility = GONE
                binding.ivError.visibility = GONE
                binding.tvError.visibility = GONE
                foundIndustries = data.industries
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
                hideViewOnNoSuccess()
                binding.filterSettingsApply.visibility = GONE
                binding.tvError.setText(R.string.no_such_industry)
                binding.ivError.setImageResource(R.drawable.il_angry_cat)
            }
        }
    }

    private fun hideViewOnNoSuccess() {
        binding.recyclerFilterIndustry.visibility = GONE
        binding.pbLoading.visibility = GONE
        binding.tvError.visibility = VISIBLE
        binding.ivError.visibility = VISIBLE
    }

    private fun initListeners() {
        binding.etSearch.addTextChangedListener(textWatcherListener())

        binding.etSearch.setOnEditorActionListener { textView, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    delay(SEARCH_DEBOUNCE_DELAY_MILS)

                }
                true
            }
            false
        }

        binding.filterSettingsApply.setOnClickListener {
            selectedIndustry?.let { it1 -> viewModel.saveIndustryFilter(it1) }
            findNavController().popBackStack()
        }

        binding.selectIndustryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivClear.setOnClickListener {
            clearSearch()
        }

    }

    private fun textWatcherListener() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!binding.etSearch.text.toString().isNullOrEmpty()) {
                binding.ivClear.setImageResource(R.drawable.ic_close)
                if (start != before) {
                    searchJob?.cancel()
                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        delay(SEARCH_DEBOUNCE_DELAY_MILS)

                        viewModel.filter(s.toString())
                    }
                }
            } else {
                binding.ivClear.setImageResource(R.drawable.ic_search)
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

    private fun clearSearch() {
        binding.etSearch.setText("")
        binding.ivClear.setImageResource(R.drawable.ic_search)
        viewModel.getIndustries()
    }

    private fun chooseIndustry(industry: Industry) {
        Log.i("Industry", "industry Choosed")
        selectedIndustry = industry
        val view = requireActivity().currentFocus
        val inputMethodManager =
            requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        if (view != null) {
            view.clearFocus()
        }
        viewModel.bufferIndustry()

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
