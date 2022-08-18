package com.example.learningandroidapp.repository

import com.example.learningandroidapp.models.UserRepo

interface UserReposRepository {
    suspend fun getUserRepos(userName: String): List<UserRepo>
}
