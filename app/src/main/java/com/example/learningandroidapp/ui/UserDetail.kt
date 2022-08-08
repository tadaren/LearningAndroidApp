package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme


@Preview(showBackground = true)
@Composable
fun UserRepositoryCardPreview() {
    val repo = UserRepo(name = "リポジトリ名", description = "description", language = "Kotlin", star = 0)
    LearningAndroidAppTheme {
        UserRepositoryCard(repo)
    }
}

@Composable
fun UserRepositoryCard(repo: UserRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth(), elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repo.name)
            Text(text = repo.description, style = MaterialTheme.typography.body2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = repo.language, modifier = Modifier.padding(end = 16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.StarOutline, contentDescription = "repository star")
                    Text(text = repo.star.toString())
                }
            }
        }
    }
}