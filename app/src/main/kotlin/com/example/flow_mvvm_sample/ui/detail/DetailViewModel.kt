package com.example.flow_mvvm_sample.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow_mvvm_sample.data.repository.RepoRepository
import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.model.Resource
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userName: String,
    private val repoName: String,
    private val repository: RepoRepository
) : ViewModel() {
    private val _loadEvent = BroadcastChannel<Unit>(Channel.BUFFERED)
    private val loadEvent = _loadEvent.asFlow()

    private val repoChannel = ConflatedBroadcastChannel<Resource<Repo>>()
    private val repo = repoChannel.asFlow()

    val isLoading = repo.map { it.isLoading }
    val isFail = repo.map { it.isFail }
    val data = repo.map { it.valueOrNull }

    init {
        loadEvent.flatMapLatest { repository.getRepoDetail(userName, repoName) }
            .onEach { repoChannel.send(it) }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            _loadEvent.send(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            _loadEvent.send(Unit)
        }
    }
}