package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectRegionBinding
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectRegionViewModel

class SelectRegionFragment : Fragment() {
    private var _binding: FragmentSelectRegionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectRegionViewModel by viewModel()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectRegionBinding.inflate(inflater, container, false)
        setPlaceholder(PlaceholdersRegionEnum.HIDE_ALL) // Добавил, что бы детект не ругался, ПОТОМ УБРАТЬ
        return binding.root
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
}
