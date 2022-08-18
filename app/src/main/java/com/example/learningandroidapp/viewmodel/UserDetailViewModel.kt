package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserDetailUiState(
    val userDetail: UserDetail? = null,
    val userRepos: List<UserRepo> = emptyList(),
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
)

@HiltViewModel
class UserDetailViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(UserDetailUiState())
        private set

    fun loadUserDetail(userName: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val userDetail = UserDetail(
                    userName = userName,
                    screenName = userName.uppercase(),
                    avatarUrl = "",
                    followers = 0,
                    following = 0
                )

                val repos = listOf(
                    UserRepo(userName, "description", "Kotlin", 12)
                )

                uiState =
                    uiState.copy(
                        isLoading = false,
                        userRepos = repos,
                        userDetail = userDetail
                    )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, hasError = true)
            }
        }
    }

    fun errorMessageShown() {
        uiState = uiState.copy(hasError = false)
    }
}
