package ru.practicum.android.diploma.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var bottomNavigationView: BottomNavigationView? = null

    private val viewModel: SearchViewModel by viewModel()

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
        viewModel.setPlaceholder(PlaceholdersSearch.SHOW_BLANK)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewModel.placeholderStatusData.observe(viewLifecycleOwner) {
            setPlaceholder(it)
        }
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
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun setPlaceholder(placeholder: PlaceholdersSearch) {
        binding.recyclerView.visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.GONE
        binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.GONE
        when (placeholder) {
            PlaceholdersSearch.SHOW_BLANK -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderBlanc).visibility = View.VISIBLE
            }

            PlaceholdersSearch.SHOW_NO_INTERNET -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoInternet).visibility = View.VISIBLE
            }

            PlaceholdersSearch.SHOW_NO_VACANCY -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderNoVacancy).visibility = View.VISIBLE
            }

            PlaceholdersSearch.SHOW_PROGRESS_CENTER -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressCenter).visibility = View.VISIBLE
            }

            PlaceholdersSearch.SHOW_PROGRESS_BOTTOM -> {
                binding.root.findViewById<ConstraintLayout>(R.id.placeholderProgressBottom).visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersSearch.SHOW_RESULT -> {
                binding.recyclerView.visibility = View.VISIBLE
            }

            PlaceholdersSearch.HIDE_ALL -> {}

        }
    }
}
