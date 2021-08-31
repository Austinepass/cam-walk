package com.orgustine.camapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.orgustine.camapp.R
import com.orgustine.camapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        setUpDrawerAndToolbar()

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }


//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        findViewById<NavigationView>(R.id.nav_view)
//            .setupWithNavController(navController)
    }

//    private fun setupDrawerLayout() {
//        navigationView.setupWithNavController(navController)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//    }
//
//    override fun onBackPressed() {
//        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }


    private fun setUpDrawerAndToolbar() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.drawer_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id != R.id.projectsFragment) {

                binding.topAppBar.setNavigationIcon(R.drawable.ic_menu_24)
            }
        }
        binding.navView.setupWithNavController(navController)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }

}