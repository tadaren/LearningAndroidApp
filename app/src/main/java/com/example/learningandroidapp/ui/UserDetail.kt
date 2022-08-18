package com.example.learningandroidapp.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learningandroidapp.R
import com.example.learningandroidapp.models.UserDetail
import com.example.learningandroidapp.models.UserRepo
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme
import com.example.learningandroidapp.viewmodel.UserDetailUiState
import com.example.learningandroidapp.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val activity = (LocalContext.current as Activity)
    val hasError = @Composable {
        val context = LocalContext.current
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.network_error_message)
            )
            viewModel.errorMessageShown()
        }
    }
    UserDetailContent(
        viewModel.uiState,
        scaffoldState,
        onClickNavigationIcon = { activity.finish() },
        hasError = hasError
    )
}

@Preview
@Composable
private fun UserDetailContentPreview() {
    val uiState = UserDetailUiState(
        userRepos = emptyList(),
        userDetail = UserDetail(
            userName = "ユーザー名",
            screenName = "スクリーンネーム",
            avatarUrl = "",
            following = 0,
            followers = 0
        )
    )
    LearningAndroidAppTheme {
        UserDetailContent(
            uiState = uiState,
            onClickNavigationIcon = {},
            hasError = {},
            scaffoldState = rememberScaffoldState()
        )
    }
}

@Composable
private fun UserDetailContent(
    uiState: UserDetailUiState,
    scaffoldState: ScaffoldState,
    onClickNavigationIcon: () -> Unit,
    hasError: @Composable () -> Unit
) {
    if (uiState.hasError) {
        hasError()
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.user_detail_page_title))
                },
                navigationIcon = {
                    IconButton(onClick = onClickNavigationIcon) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val repos = uiState.userRepos
            val userDetail = uiState.userDetail ?: throw NullPointerException()
            Column {
                UserInfo(userDetail)
                UserRepositoryCardList(repos = repos)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoPreview() {
    val userDetail = UserDetail(
        userName = "ユーザー名",
        screenName = "スクリーンネーム",
        avatarUrl = "",
        followers = 0,
        following = 0
    )
    LearningAndroidAppTheme {
        UserInfo(userDetail)
    }
}

@Composable
private fun UserInfo(userDetail: UserDetail) {
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
                painter = painterResource(id = R.drawable.ic_supervised_user_circle),
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp),
                tint = Color.Unspecified
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(userDetail.followers.toString())
                    }
                    append(stringResource(R.string.followers))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.dot))
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(userDetail.following.toString())
                    }
                    append(stringResource(R.string.following))
                },
                style = MaterialTheme.typography.body2
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun UserRepositoryCardListEmptyPreview() {
    LearningAndroidAppTheme {
        UserRepositoryCardList(repos = emptyList())
    }
}

@Composable
private fun UserRepositoryCardList(repos: List<UserRepo>) {
    if (repos.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.empty_repository_message))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items = repos) { repo ->
                UserRepositoryCard(repo = repo)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserRepositoryCardPreview() {
    val repo = UserRepo(name = "リポジトリ名", description = "description", language = "Kotlin", star = 0)
    LearningAndroidAppTheme {
        UserRepositoryCard(repo)
    }
}

@Composable
private fun UserRepositoryCard(repo: UserRepo) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 3.dp
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
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(text = repo.star.toString())
                }
            }
        }
    }
}
