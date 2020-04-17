package com.example.flow_mvvm_sample.ui.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flow_mvvm_sample.databinding.RepoListItemBinding
import com.example.flow_mvvm_sample.model.Repo


class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
    private var list: List<Repo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepoListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.also {
            it.repo = list[position]
            it.executePendingBindings()
        }
    }

    fun setList(list: List<Repo>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: RepoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}