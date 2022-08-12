package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
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

        uiState = uiState.copy(userRepos = repos, userDetail = userDetail)
    }
}
