package com.example.learningandroidapp.repository

import com.example.learningandroidapp.models.UserDetail

interface UserDetailRepository {
    suspend fun getUserDetail(userName: String): UserDetail
}
