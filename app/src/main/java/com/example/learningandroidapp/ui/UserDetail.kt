package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme

@Preview
@Composable
fun UserDetailScreenPreview() {
    LearningAndroidAppTheme {
        UserDetailScreen("ユーザー名")
    }
}

@Composable
fun UserDetailScreen(userName: String) {
    val repos = listOf(
        UserRepo(name = "リポジトリ1", description = "description1", language = "Kotlin", star = 1),
        UserRepo(name = "リポジトリ2", description = "description2", language = "Java", star = 11),
        UserRepo(name = "リポジトリ3", description = "description3", language = "Scala", star = 111)
    )
    val userDetail = UserDetail(
        userName = userName,
        screenName = "スクリーンネーム",
        avatarUrl = "",
        followers = 0,
        following = 0,
        repos = repos
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "ユーザーリポジトリ")
                }
            )
        }) {
        Column {
            UserInfo(userDetail)
            UserRepositoryCardList(repos = userDetail.repos)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    val userDetail = UserDetail(
        userName = "ユーザー名",
        screenName = "スクリーンネーム",
        avatarUrl = "",
        followers = 0,
        following = 0,
        repos = emptyList()
    )
    LearningAndroidAppTheme {
        UserInfo(userDetail)
    }
}

@Composable
fun UserInfo(userDetail: UserDetail) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = userDetail.avatarUrl,
                contentDescription = "avatar",
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(text = userDetail.screenName)
                Text(text = userDetail.userName, style = MaterialTheme.typography.body2)
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
                    append(userDetail.followers.toString())
                }
                append(" followers")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("・")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(userDetail.following.toString())
                }
                append(" following")
            })
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserRepositoryCardListEmptyPreview() {
    LearningAndroidAppTheme {
        UserRepositoryCardList(repos = emptyList())
    }
}

@Composable
fun UserRepositoryCardList(repos: List<UserRepo>) {
    if (repos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "リポジトリはありません")
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = repos) { repo ->
                UserRepositoryCard(repo = repo)
            }
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
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
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