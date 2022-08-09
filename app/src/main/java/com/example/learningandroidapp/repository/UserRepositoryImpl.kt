package com.example.learningandroidapp.repository

import com.example.learningandroidapp.api.GitHubApiService
import com.example.learningandroidapp.models.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApiService
) : UserRepository {
    override suspend fun getUserList(userName: String): List<User> {
        val response = gitHubApi.getUsers(userName)
        val userList = response.items.map {
            User(
                userName = it.login,
                avatarUrl = it.avatarUrl
            )
        }
        return userList
    }
}
