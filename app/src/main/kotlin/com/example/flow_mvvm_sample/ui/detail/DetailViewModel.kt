package com.example.flow_mvvm_sample.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow_mvvm_sample.data.repository.RepoRepository
import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.model.Resource
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DetailViewModel(
    private val userName: String,
    private val repoName: String,
    private val repository: RepoRepository
) : ViewModel() {
    private val repoChannel = ConflatedBroadcastChannel<Resource<Repo>>()
    private val repo get() = repoChannel.asFlow()

    val isLoading = repo.map { it.isLoading }
    val data = repo.map { it.valueOrNull }

    init {
        repository.getRepoDetail(userName, repoName)
            .onEach { repoChannel.send(it) }
            .launchIn(viewModelScope)
    }
}