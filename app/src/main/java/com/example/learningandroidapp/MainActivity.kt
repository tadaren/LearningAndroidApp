package com.example.learningandroidapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.learningandroidapp.ui.UserSearchScreen
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningAndroidAppTheme {
                UserSearchScreen(onNavigate = {
                    val intent = Intent(this, UserDetailActivity::class.java)
                    intent.putExtra("userName", it)
                    this.startActivity(intent)
                })
            }
        }
    }
}