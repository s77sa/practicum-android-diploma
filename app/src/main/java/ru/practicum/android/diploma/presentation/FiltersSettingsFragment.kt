package ru.practicum.android.diploma.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentFiltersSettingsBinding

class FiltersSettingsFragment : Fragment() {
    private var _binding: FragmentFiltersSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
