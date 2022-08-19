package com.example.learningandroidapp.api

data class UserRepoApiModel(
    val name: String,
    val description: String?,
    val forked: Boolean,
    val language: String?,
    val stargazersCount: Int,
)
