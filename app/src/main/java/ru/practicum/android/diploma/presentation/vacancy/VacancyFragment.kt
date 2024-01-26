package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding


class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun createArgs(vacancyId: String?) = bundleOf(VACANCY_ID to vacancyId)
        private const val VACANCY_ID = "VACANCY_ID"
    }
}
