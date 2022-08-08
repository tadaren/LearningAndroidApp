package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme


@Preview(showBackground = true)
@Composable
fun UserRepositoryCardPreview() {
    LearningAndroidAppTheme {
        UserRepositoryCard()
    }
}

@Composable
fun UserRepositoryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(), elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "リポジトリ名")
            Text(text = "description", style = MaterialTheme.typography.body2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Kotlin", modifier = Modifier.padding(end = 16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.StarOutline, contentDescription = "repository star")
                    Text(text = "star")
                }
            }
        }
    }
}