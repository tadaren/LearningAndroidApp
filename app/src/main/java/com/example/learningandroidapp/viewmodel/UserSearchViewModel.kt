package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningandroidapp.models.User
import com.example.learningandroidapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UserSearchUiState(
    val userList: List<User> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var uiState by mutableStateOf(UserSearchUiState())
        private set

    fun searchUser(text: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            val userList = userRepository.getUserList(text)
            uiState = uiState.copy(isLoading = false, userList = userList)
        }
    }
}