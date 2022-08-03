package com.example.learningandroidapp.api

import com.example.learningandroidapp.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private val gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()
private const val BASE_URL = "https://api.github.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

data class GetUsersResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<User>
)

interface GitHubApiService {
    @Headers(
        "Authorization: token ${BuildConfig.GITHUB_TOKEN}",
        "Accept: application/vnd.github+json"
    )
    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): GetUsersResponse

}

object GitHubApi {
    val retrofitService: GitHubApiService by lazy {
        retrofit.create(GitHubApiService::class.java)
    }
}
