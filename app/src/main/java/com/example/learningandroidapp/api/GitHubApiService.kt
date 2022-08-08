package com.example.learningandroidapp.api

import com.example.learningandroidapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GitHubApiService {
    @Headers(
        "Authorization: token ${BuildConfig.GITHUB_TOKEN}",
        "Accept: application/vnd.github+json"
    )
    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): GetUsersResponse

}
