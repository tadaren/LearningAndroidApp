package com.example.learningandroidapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.repository.UserDetailRepository
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
class UserDetailViewModel @Inject constructor(
    private val userDetailRepository: UserDetailRepository
) : ViewModel() {
    var uiState by mutableStateOf(UserDetailUiState())
        private set

    fun loadUserDetail(userName: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            uiState = try {
                val userDetail = userDetailRepository.getUserDetail(userName)
                val repos = userDetail.repos.filter { !it.isForked }

                uiState.copy(
                    isLoading = false,
                    userRepos = repos,
                    userDetail = userDetail
                )
            } catch (e: Exception) {
                uiState.copy(isLoading = false, hasError = true)
            }
        }
    }

    fun errorMessageShown() {
        uiState = uiState.copy(hasError = false)
    }
}
