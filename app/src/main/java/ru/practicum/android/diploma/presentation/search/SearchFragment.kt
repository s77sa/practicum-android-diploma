package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.models.SearchState
import ru.practicum.android.diploma.presentation.search.recyclerview.VacanciesAdapter
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.SharedViewModel
import ru.practicum.android.diploma.presentation.util.debounce
import ru.practicum.android.diploma.presentation.vacancy.VacancyFragment

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var bottomNavigationView: BottomNavigationView? = null
    private val viewModel: SearchViewModel by viewModel()
    private var vacancies = ArrayList<Vacancy>()
    private var onVacancyClickDebounce: ((Vacancy) -> Unit)? = null
    private val vacancyAdapter =
        VacanciesAdapter(clickListener = { data -> onVacancyClickDebounce?.invoke(data) }, vacancies)
    private var searchInput: EditText? = null
    private var iconSearch: ImageView? = null
    private var foundVacancies = 0
    private var isNeedAddItems = true
    private var lastSearchText = ""
    private var newSearchText = ""
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        searchInput = binding.searchInput
        iconSearch = binding.ivClear
        initListeners()
        initObservers()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initClickListener()
        sharedViewModel.isFilterOn.observe(viewLifecycleOwner, Observer { isFilterOn ->
            if (isFilterOn) {
                binding.filterButton.setImageResource(R.drawable.ic_filter_on)
            } else {
                binding.filterButton.setImageResource(R.drawable.ic_filter_off)
            }
        })
    }

    private fun initObservers() {
        viewModel.placeholderStatusData.observe(viewLifecycleOwner) {
            setPlaceholder(it)
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
            updateScreen(it)
        }
    }

    private fun initListeners() {
        searchInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setIconToTextView()
                newSearchText = s.toString()
                viewModel.searchDebounce(newSearchText)
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemsCount = vacancyAdapter.itemCount
                if (dy > 0 && itemsCount > 0) {
                    val pos =
                        (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (pos >= itemsCount - 1) {
                        isNeedAddItems = true
                        viewModel.onNextPage()
                    }
                }
            }
        })

        iconSearch?.setOnClickListener {
            clearSearch()
        }
    }

    private fun clearSearch() {
        viewModel.clearSearchResult()
        showFoundResultBar()
        searchInput?.text?.clear()
    }

    private fun setIconToTextView() {
        val len = searchInput?.text?.length
        if (len != null) {
            if (len > 0) {
                iconSearch?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_close))
            } else {
                iconSearch?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_search))
            }
        }
    }

    private fun setPlaceholder(placeholder: PlaceholdersSearchEnum) {
        binding.recyclerView.visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.GONE
        when (placeholder) {
            PlaceholdersSearchEnum.SHOW_BLANK -> {
                binding.recyclerView.visibility = View.GONE
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.VISIBLE
            }

            PlaceholdersSearchEnum.SHOW_NO_INTERNET -> {
                if (vacancies.size > 0) {
                    showAnackBar()
                    binding.recyclerView.visibility = View.VISIBLE
                } else {
                    binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.VISIBLE
                }
            }

            PlaceholdersSearchEnum.SHOW_NO_VACANCY -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.VISIBLE
            }

            PlaceholdersSearchEnum.SHOW_PROGRESS_CENTER -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.VISIBLE
            }

            PlaceholdersSearchEnum.SHOW_PROGRESS_BOTTOM -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersSearchEnum.SHOW_RESULT -> {
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersSearchEnum.HIDE_ALL -> {}
        }
    }

    private fun showAnackBar() {
        Snackbar.make(binding.recyclerView, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
            .show()
        updateScreen(SearchState.Content(vacancies, foundVacancies))
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = vacancyAdapter
    }

    private fun updateScreen(state: SearchState) {
        when (state) {
            is SearchState.Content -> {
                if (!lastSearchText.equals(newSearchText)) {
                    vacancies.clear()
                    lastSearchText = newSearchText
                }
                foundVacancies = state.foundItems!!
                if (state.vacancies.isNotEmpty() && !vacancies.contains(state.vacancies[0])) {
                    vacancies.addAll(state.vacancies)
                }
                vacancyAdapter.notifyDataSetChanged()
                if (binding.searchInput.text.isNotEmpty()) showFoundResultBar(foundVacancies)
            }

            is SearchState.Empty -> {
                showFoundResultBar(0)
            }

            else -> {}
        }
        hideKeyBoard()
    }

    private fun showFoundResultBar(foundItems: Int? = null) {
        when (foundItems) {
            null -> {
                binding.foundResults.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                Log.d(TAG, "showFoundResultBar null")
            }

            0 -> {
                binding.foundResults.text = getString(R.string.status_no_results)
                binding.foundResults.isVisible = true
                Log.d(TAG, "showFoundResultBar 0")
            }

            else -> {
                var value = getString(R.string.status_results)
                value = value.replace(FOUND_REPLACE_PATTERN, foundItems.toString())
                binding.foundResults.text = value
                binding.foundResults.isVisible = true
                Log.d(TAG, "showFoundResultBar else")
            }
        }
    }

    private fun initClickListener() {
        onVacancyClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope, false
        ) { vacancy ->
            val bundle = Bundle().apply {
                putString(VacancyFragment.VACANCY_ID, vacancy.id)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_vacancyFragment,
                bundle
            )
        }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtersSettingsFragment)
        }
    }


    private fun hideKeyBoard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
        binding.searchInput.clearFocus()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFilter()
        if (vacancies.size > 0) {
            setPlaceholder(PlaceholdersSearchEnum.SHOW_RESULT)
        } else {
            setPlaceholder(PlaceholdersSearchEnum.SHOW_BLANK)
            binding.foundResults.visibility = View.GONE
        }
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 300L
        const val FOUND_REPLACE_PATTERN = "[found]"
        val TAG: String = SearchFragment::class.java.simpleName
    }
}
