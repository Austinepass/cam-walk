package com.orgustine.camapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orgustine.camapp.R
import com.orgustine.camapp.databinding.FragmentHomeBinding
import com.orgustine.camapp.databinding.FragmentProjectsBinding

class ProjectsFragment : Fragment(R.layout.fragment_projects) {
    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProjectsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}