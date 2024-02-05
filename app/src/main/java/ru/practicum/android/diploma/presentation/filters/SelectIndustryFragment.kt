package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSelectIndustryBinding
import ru.practicum.android.diploma.presentation.filters.viewmodel.SelectIndustryViewModel

class SelectIndustryFragment : Fragment() {
    private var _binding: FragmentSelectIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectIndustryViewModel by viewModel()

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectIndustryBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root
    }

    private fun initClickListeners() {
        binding.selectIndustryBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
