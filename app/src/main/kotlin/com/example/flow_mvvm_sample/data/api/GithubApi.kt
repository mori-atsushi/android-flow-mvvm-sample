package com.example.flow_mvvm_sample.data.api

import com.example.flow_mvvm_sample.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    companion object {
        const val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    @GET("users/{user}/repos")
    suspend fun getRepoList(
        @Path("user") user: String?
    ): List<Repo>

    @GET("/repos/{user}/{repo_name}")
    suspend fun getRepoDetails(
        @Path("user") user: String?,
        @Path("repo_name") repoName: String?
    ): Repo
}