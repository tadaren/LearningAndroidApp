package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.models.UserDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApiService
) : UserDetailRepository {
    override suspend fun getUserDetail(userName: String): UserDetail {
        // TODO
        return UserDetail(
            userName = userName,
            screenName = userName.uppercase(),
            avatarUrl = "",
            followers = 2,
            following = 2
        )
    }
}
