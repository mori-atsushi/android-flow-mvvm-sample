package com.example.flow_mvvm_sample.data.api

import com.example.flow_mvvm_sample.model.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    companion object {
        const val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    @GET("users/{user}/repos")
    suspend fun getProjectList(
        @Path("user") user: String?
    ): List<Repository>

    @GET("/repos/{user}/{repo_name}")
    suspend fun getProjectDetails(
        @Path("user") user: String?,
        @Path("repo_name") repoName: String?
    ): Repository
}