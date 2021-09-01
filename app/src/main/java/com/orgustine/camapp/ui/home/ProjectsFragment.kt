package com.orgustine.camapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        TODO("Not yet implemented")
    }

}