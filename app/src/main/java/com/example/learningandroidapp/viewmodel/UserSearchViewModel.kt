package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


data class UserSearchUiState(
    val userList: List<String> = emptyList(),
    val isLoading: Boolean = false
)

class UserSearchViewModel : ViewModel() {
    var uiState by mutableStateOf(UserSearchUiState())
        private set

    fun searchUser(text: String) {
        // TODO call API(repository)
    }
}