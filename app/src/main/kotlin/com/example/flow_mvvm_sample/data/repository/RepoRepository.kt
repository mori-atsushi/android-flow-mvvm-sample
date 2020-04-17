package com.example.flow_mvvm_sample.data.repository

import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.model.Resource
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getRepoList(userId: String): Flow<Resource<List<Repo>>>
    fun getRepoDetail(userId: String, projectId: String): Flow<Resource<Repo>>
}