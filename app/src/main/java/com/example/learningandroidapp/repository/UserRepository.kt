package com.example.learningandroidapp.repository

import com.example.learningandroidapp.models.User


interface UserRepository {
    suspend fun getUserList(userName: String): List<User>
}
