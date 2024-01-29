package ru.practicum.android.diploma.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.domain.models.FavoriteVacancyState
import ru.practicum.android.diploma.domain.models.FavouriteStates
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.search.SearchFragment
import ru.practicum.android.diploma.presentation.search.recyclerview.VacanciesAdapter
import ru.practicum.android.diploma.presentation.util.debounce
import ru.practicum.android.diploma.presentation.vacancy.VacancyFragment

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouriteFragmentViewModel by viewModel()
    private var vacancies = ArrayList<Vacancy>()
    private var onVacancyClickDebounce: ((Vacancy) -> Unit)? = null
    private val vacancyAdapter =
        VacanciesAdapter(clickListener = { data -> onVacancyClickDebounce?.invoke(data) }, vacancies)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorite.adapter = vacancyAdapter

        initClickListener()

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state.first) {
                FavouriteStates.Empty -> {
                    binding.apply {
                        placeHolderFavorite.visibility = View.VISIBLE
                        placeholderError.visibility = View.GONE
                        recyclerViewFavorite.visibility = View.GONE
                    }
                }

                FavouriteStates.Error -> {
                    binding.apply {
                        placeHolderFavorite.visibility = View.GONE
                        placeholderError.visibility = View.VISIBLE
                        recyclerViewFavorite.visibility = View.GONE
                    }
                }

                is FavouriteStates.Success -> {
                    binding.apply {
                        placeHolderFavorite.visibility = View.GONE
                        placeholderError.visibility = View.GONE
                        recyclerViewFavorite.visibility = View.VISIBLE
                        //vacancyAdapter.vacancies = state.second
                        vacancyAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        viewModel.loadFavourites()
    }

    private fun initClickListener() {
        onVacancyClickDebounce = debounce(
            SearchFragment.CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope, false
        ) { vacancy ->
            val bundle = Bundle().apply {
                putString(VacancyFragment.VACANCY_ID, vacancy.id)
            }
            findNavController().navigate(
                R.id.action_favouriteFragment_to_vacancyFragment,
                bundle
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

