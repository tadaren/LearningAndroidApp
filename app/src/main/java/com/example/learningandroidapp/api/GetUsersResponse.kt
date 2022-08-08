package com.example.learningandroidapp.api

data class GetUsersResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<UserApiModel>
)