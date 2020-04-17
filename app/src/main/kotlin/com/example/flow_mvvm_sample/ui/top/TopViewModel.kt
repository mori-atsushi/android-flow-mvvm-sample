package com.example.flow_mvvm_sample.ui.top

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

class TopViewModel(
    private val repository: RepoRepository
) : ViewModel() {
    private val resourceChannel = ConflatedBroadcastChannel<Resource<List<Repo>>>()
    private val resource get() = resourceChannel.asFlow()
    val isLoading = resource.map {
        it is Resource.Loading
    }
    val data = resource.map {
        (it as? Resource.Success)?.value
    }

    init {
        repository.getRepoList("Google")
            .onEach { resourceChannel.send(it) }
            .launchIn(viewModelScope)
    }
}