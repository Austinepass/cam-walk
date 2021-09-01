package com.orgustine.camapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orgustine.camapp.R
import com.orgustine.camapp.data.ProjectAdapter.ProjectViewHolder
import com.orgustine.camapp.data.model.Project
import com.orgustine.camapp.databinding.ProjectItemLayoutBinding

class ProjectAdapter(private val listener: OnItemClickListener, private val device: Boolean = true)
    : ListAdapter<Project, ProjectViewHolder>(DiffCallback()) {

    inner class ProjectViewHolder(private val binding: ProjectItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            binding.apply {
                title.text = project.title
                if (!device) {downloadIcon.setImageResource(R.drawable.cloud_download)}
//                Glide.with(binding.root.context)
//                    .load(newsResult.media[0].mediaMetadata[2].url)
//                    .into(image)
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(adapterPosition)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding =
            ProjectItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    class DiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Project, newItem: Project) =
            oldItem == newItem
    }
}