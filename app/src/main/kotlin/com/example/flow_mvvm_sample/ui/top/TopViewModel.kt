package com.example.flow_mvvm_sample.ui.top

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

class TopViewModel(
    private val repository: RepoRepository
) : ViewModel() {
    private val _userName = ConflatedBroadcastChannel("Google")
    val userName get() = _userName.asFlow()

    private val _submitEvent = BroadcastChannel<Unit>(Channel.BUFFERED)
    private val submitEvent get() = _submitEvent.asFlow()

    private val _resource = ConflatedBroadcastChannel<Resource<List<Repo>>>()
    private val resource get() = _resource.asFlow()
    val isLoading = resource.map {
        it.isLoading
    }
    val isFail = resource.map {
        it.isFail
    }
    val data = resource.map {
        it.valueOrNull.orEmpty()
    }

    init {
        submitEvent
            .map { _userName.value }
            .flatMapLatest { repository.getRepoList(it) }
            .onEach { _resource.send(it) }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            _submitEvent.send(Unit)
        }
    }

    fun setUserName(userName: String) {
        viewModelScope.launch {
            _userName.send(userName)
        }
    }

    fun submit() {
        viewModelScope.launch {
            _submitEvent.send(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            _submitEvent.send(Unit)
        }
    }
}