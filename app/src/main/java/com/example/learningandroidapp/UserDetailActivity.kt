package com.example.learningandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.learningandroidapp.ui.UserDetailScreen
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme

class UserDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningAndroidAppTheme {
                UserDetailScreen(intent?.extras?.getString("userName").toString())
            }
        }
    }
}