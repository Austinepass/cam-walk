package com.orgustine.camapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.orgustine.camapp.R
import com.orgustine.camapp.activity.MainActivity
import com.orgustine.camapp.data.ProjectAdapter
import com.orgustine.camapp.data.ProjectAdapter.*
import com.orgustine.camapp.data.ProjectViewModel
import com.orgustine.camapp.databinding.FragmentHomeBinding
import com.orgustine.camapp.databinding.FragmentProjectsBinding

class ProjectsFragment : Fragment(R.layout.fragment_projects), OnItemClickListener {
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProjectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProjectsBinding.bind(view)

        setDeviceList()
        setCloudList()

        binding.createProjectBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private fun setDeviceList() {
        val mAdapter = ProjectAdapter(this)
        mAdapter.submitList(viewModel.deviceList)
        binding.onDeviceList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }
    private fun setCloudList() {
        val mAdapter = ProjectAdapter(this, false)
        mAdapter.submitList(viewModel.cloudList)
        binding.onCloudList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(id: Int) {
        Log.i("Root", "Root clicked")
    }

    override fun onMoreClick(v: View) {
        showMenu(v, R.menu.project_list_menu)
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            when (menuItem.itemId) {
                R.id.capture -> {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    true
                }
                R.id.delete -> {
                    Snackbar.make(requireView(), "Deleting Project...", Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.detailsFragment)
                    true
                }
            }
        }
//        popup.setOnDismissListener {
//            // Respond to popup being dismissed.
//        }
        // Show the popup menu.
        popup.show()
    }

}