package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningandroidapp.api.GitHubApi
import kotlinx.coroutines.launch


data class UserSearchUiState(
    val userList: List<String> = emptyList(),
    val isLoading: Boolean = false
)

class UserSearchViewModel : ViewModel() {
    var uiState by mutableStateOf(UserSearchUiState())
        private set

    fun searchUser(text: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            // TODO repository経由で呼ぶように変更する
            val result = GitHubApi.retrofitService.getUsers(text)
            val items = result.items
            val usernames = items.map { it.login }
            uiState = uiState.copy(isLoading = false, userList = usernames)
        }
    }
}