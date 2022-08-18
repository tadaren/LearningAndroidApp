package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.models.UserRepo
import javax.inject.Inject

class UserReposRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApiService
) : UserReposRepository {
    override suspend fun getUserRepos(userName: String): List<UserRepo> {
        return listOf(
            UserRepo(userName, "description", "Kotlin", 24)
        )
    }
}
