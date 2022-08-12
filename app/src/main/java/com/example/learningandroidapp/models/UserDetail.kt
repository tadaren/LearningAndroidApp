package com.example.learningandroidapp.models

data class UserDetail(
    val userName: String,
    val screenName: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int
)
