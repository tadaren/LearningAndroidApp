package com.example.learningandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.learningandroidapp.ui.UserDetailScreen
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme
import com.example.learningandroidapp.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userName = intent?.extras?.getString("userName") ?: throw NullPointerException()
        val viewModel by viewModels<UserDetailViewModel>()
        viewModel.loadUserDetail(userName)
        setContent {
            LearningAndroidAppTheme {
                UserDetailScreen(viewModel = viewModel)
            }
        }
    }
}