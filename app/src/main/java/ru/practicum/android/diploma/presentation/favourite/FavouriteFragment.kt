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
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favourite.viewmodel.FavouriteFragmentViewModel
import ru.practicum.android.diploma.presentation.search.recyclerview.VacanciesAdapter
import ru.practicum.android.diploma.presentation.vacancy.VacancyFragment

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouriteFragmentViewModel by viewModel()
    private var favoriteAdapter: VacanciesAdapter? = null

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

        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewFavorite.adapter = favoriteAdapter

        viewModel.getAllVacancies()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is FavoriteVacancyState.Empty -> {
                        binding.placeHolderFavorite.visibility = View.VISIBLE
                        binding.recyclerViewFavorite.visibility = View.GONE
                    }

                    is FavoriteVacancyState.VacancyLoaded -> {
                        binding.placeHolderFavorite.visibility = View.GONE
                        binding.recyclerViewFavorite.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun onClick(vacancy: Vacancy) {
        findNavController().navigate(
            R.id.action_favouriteFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancy.id)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllVacancies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
