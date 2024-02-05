package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectWorkplaceBinding
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectWorkplaceViewModel

class SelectWorkplaceFragment : Fragment() {
    private var _binding: FragmentSelectWorkplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectWorkplaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectWorkplaceBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root
    }

    private fun initClickListeners(){
        binding.selectWorkplaceBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.selectCountryBottom.setOnClickListener {
            findNavController().navigate(R.id.action_selectWorkplaceFragment_to_selectCountryFragment)
        }

        binding.selectRegionButton.setOnClickListener {
            findNavController().navigate(R.id.action_selectWorkplaceFragment_to_selectRegionFragment)
        }
    }
}
