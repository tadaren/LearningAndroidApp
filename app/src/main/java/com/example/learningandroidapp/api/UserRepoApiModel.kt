package com.example.learningandroidapp.api

data class UserRepoApiModel(
    val name: String,
    val description: String?,
    val fork: Boolean,
    val language: String?,
    val stargazersCount: Int,
    val htmlUrl: String,
)
