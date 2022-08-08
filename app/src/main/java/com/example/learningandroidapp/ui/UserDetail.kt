package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    LearningAndroidAppTheme {
        UserInfo()
    }
}

@Composable
fun UserInfo() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/20397503?v=4",
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(text = "スクリーンネーム")
                Text(text = "ユーザー名", style = MaterialTheme.typography.body2)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.SupervisedUserCircle,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("15")
                }
                append(" followers")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("・")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("24")
                }
                append(" following")
            })
        }
    }

}

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