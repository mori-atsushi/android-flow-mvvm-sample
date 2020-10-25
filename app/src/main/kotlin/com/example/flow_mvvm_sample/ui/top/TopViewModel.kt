package com.example.flow_mvvm_sample.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flow_mvvm_sample.data.repository.RepoRepository
import com.example.flow_mvvm_sample.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TopViewModel(
    private val repository: RepoRepository
) : ViewModel() {
    private val _userName = MutableStateFlow("Google")
    val userName: Flow<String> = _userName

    private val submitEvent = MutableSharedFlow<Unit>()

    private val resource = submitEvent
        .map { _userName.value }
        .flatMapLatest { repository.getRepoList(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Resource.Loading)

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
        viewModelScope.launch {
            submitEvent.emit(Unit)
        }
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun submit() {
        viewModelScope.launch {
            submitEvent.emit(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            submitEvent.emit(Unit)
        }
    }
}