package com.example.flow_mvvm_sample.data.repository

import com.example.flow_mvvm_sample.data.api.GithubApi
import com.example.flow_mvvm_sample.model.Repo
import com.example.flow_mvvm_sample.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepoRepositoryImpl(
    private val api: GithubApi
) : RepoRepository {
    override fun getRepoList(
        userId: String
    ): Flow<Resource<List<Repo>>> = flow {
        emit(Resource.Loading)
        val resource = try {
            val response = api.getRepoList(userId)
            Resource.Success(response)
        } catch (e: Throwable) {
            Resource.Fail(e)
        }
        emit(resource)
    }

    override fun getRepoDetail(
        userId: String,
        projectId: String
    ): Flow<Resource<Repo>> = flow {
        emit(Resource.Loading)
        val resource = try {
            val response = api.getRepoDetails(userId, projectId)
            Resource.Success(response)
        } catch (e: Throwable) {
            Resource.Fail(e)
        }
        emit(resource)
    }
}