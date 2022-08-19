package com.example.learningandroidapp.api

data class UserDetailApiModel(
    val login: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int
)
