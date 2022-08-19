package com.example.learningandroidapp.api

import com.example.learningandroidapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @Headers(
        "Authorization: token ${BuildConfig.GITHUB_TOKEN}",
        "Accept: application/vnd.github+json"
    )
    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): GetUsersResponse

    @Headers(
        "Authorization: token ${BuildConfig.GITHUB_TOKEN}",
        "Accept: application/vnd.github+json"
    )
    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username") userName: String): List<UserRepoApiModel>

    @Headers(
        "Authorization: token ${BuildConfig.GITHUB_TOKEN}",
        "Accept: application/vnd.github+json"
    )
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") userName: String): UserDetailApiModel
}
