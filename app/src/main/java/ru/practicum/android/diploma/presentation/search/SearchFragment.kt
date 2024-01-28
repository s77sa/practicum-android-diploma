package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.models.SearchState
import ru.practicum.android.diploma.presentation.search.recyclerview.VacanciesAdapter
import ru.practicum.android.diploma.presentation.search.viewmodel.SearchViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        initListeners()
        initObservers()
        viewModel.setPlaceholder(PlaceholdersEnum.SHOW_BLANK)
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
    }

    private fun initObservers() {
        viewModel.placeholderStatusData.observe(viewLifecycleOwner) {
            setPlaceholder(it)
        }
        viewModel.observeState().observe(viewLifecycleOwner) { updateScreen(it) }
    }

    private fun initListeners() {
        binding.searchInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                bottomNavigationView?.isVisible = false
            }
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bottomNavigationView?.isVisible = s.isNullOrEmpty()
                viewModel.searchDebounce(s.toString())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    // binding.progressBar.visibility = View.VISIBLE
                    val pos =
                        (binding.recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onNextPage()
                    }
                }
            }
        })
    }

    private fun setPlaceholder(placeholder: PlaceholdersEnum) {
        binding.recyclerView.visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.GONE
        when (placeholder) {
            PlaceholdersEnum.SHOW_BLANK -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.VISIBLE
            }

            PlaceholdersEnum.SHOW_NO_INTERNET -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.VISIBLE
            }

            PlaceholdersEnum.SHOW_NO_VACANCY -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.VISIBLE
            }

            PlaceholdersEnum.SHOW_PROGRESS_CENTER -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.VISIBLE
            }

            PlaceholdersEnum.SHOW_PROGRESS_BOTTOM -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersEnum.SHOW_RESULT -> {
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersEnum.HIDE_ALL -> {}

        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = vacancyAdapter
    }

    private fun updateScreen(state: SearchState) {
        when (state) {
            is SearchState.Content -> {
                vacancies.clear()
                vacancies.addAll(state.vacancies)
                vacancyAdapter.notifyDataSetChanged()
                binding.foundResults.text = "Найдено ${state.foundItems} вакансий"
                binding.foundResults.isVisible = true
                hideKeyBoard()
            }
            else -> {}
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
    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
        binding.searchInput.clearFocus()
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 300L
    }
}
