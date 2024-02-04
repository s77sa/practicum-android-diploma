package ru.practicum.android.diploma.presentation.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.domain.models.FilterSettings

class RootActivity : AppCompatActivity(), FilterSettingsStorage {
    val binding by lazy {
        ActivityRootBinding.inflate(layoutInflater)
    }

    private var filterStorage: FilterSettings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment,
                R.id.favouriteFragment,
                R.id.teamFragment -> showBottomNavigationView()

                else -> hideBottomNavigationView()
            }
        }
    }

    private fun showBottomNavigationView() {
        binding.bottomNavigationView.isVisible = true
    }

    private fun hideBottomNavigationView() {
        binding.bottomNavigationView.isVisible = false
    }

    override fun getFilters(): FilterSettings? {
        return filterStorage
    }

    override fun setFilters(filterSettings: FilterSettings) {
        filterStorage = filterSettings
    }

}
