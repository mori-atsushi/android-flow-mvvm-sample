package com.example.flow_mvvm_sample.ui.detail

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
class DetailViewModel(
    private val userName: String,
    private val repoName: String,
    private val repository: RepoRepository
) : ViewModel() {
    private val loadEvent = MutableSharedFlow<Unit>()

    private val repo = loadEvent
        .flatMapLatest { repository.getRepoDetail(userName, repoName) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Resource.Loading)

    val isLoading = repo.map { it.isLoading }
    val isFail = repo.map { it.isFail }
    val data = repo.map { it.valueOrNull }

    init {
        viewModelScope.launch {
            loadEvent.emit(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch {
            loadEvent.emit(Unit)
        }
    }
}