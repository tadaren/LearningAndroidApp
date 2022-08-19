package com.example.learningandroidapp.repository

import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo

interface UserDetailRepository {
    suspend fun getUserDetail(userName: String): UserDetail

    suspend fun getUserRepos(userName: String): List<UserRepo>
}
