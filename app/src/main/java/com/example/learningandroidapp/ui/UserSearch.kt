package com.example.learningandroidapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learningandroidapp.R
import com.example.learningandroidapp.models.User
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme
import com.example.learningandroidapp.viewmodel.UserSearchUiState
import com.example.learningandroidapp.viewmodel.UserSearchViewModel


@Preview(showSystemUi = true)
@Composable
fun UserSearchScreenPreview() {
    LearningAndroidAppTheme {
        UserSearchScreen()
    }
}

@Composable
fun UserSearchScreen(viewModel: UserSearchViewModel = viewModel()) {
    val uiState = viewModel.uiState
    val searchUser = { text: String ->
        viewModel.searchUser(text)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.appbar_text)
                    )
                }
            )
        }
    ) {
        Column {
            SearchBox(onSearch = searchUser)
            UserSearchContent(uiState = uiState)
        }
    }
}

@Composable
fun UserSearchContent(uiState: UserSearchUiState) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        UserList(userList = uiState.userList)
    }
}

@Preview
@Composable
fun UserSearchContentLoadingPreview() {
    LearningAndroidAppTheme {
        Surface {
            UserSearchContent(uiState = UserSearchUiState(isLoading = true))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(modifier: Modifier = Modifier, onSearch: (String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    val isEmpty by remember {
        derivedStateOf { text.isEmpty() }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            value = text,
            onValueChange = { text = it },
            label = { Text(text = stringResource(R.string.searchbox_label)) },
            singleLine = true,
            trailingIcon = {
                if (!isEmpty) {
                    IconButton(onClick = { text = "" }) {
                        Icon(
                            Icons.Filled.Cancel,
                            contentDescription = stringResource(R.string.searchbox_clear_button_text)
                        )
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                // キーボードを閉じる
                keyboardController?.hide()
            })
        )
        Button(modifier = Modifier.padding(end = 16.dp), onClick = { onSearch(text) }) {
            Text(text = stringResource(R.string.searchbox_button_text))
        }
    }
}

@Preview
@Composable
fun UserListPreview() {
    val userList = List(12) {
        User(userName = "ユーザー$it", avatarUrl = "")
    }
    LearningAndroidAppTheme {
        Surface {
            UserList(userList = userList)
        }
    }
}

@Preview
@Composable
fun EmptyUserListPreview() {
    val userList = List(0) {
        User(userName = "ユーザー$it", avatarUrl = "")
    }
    LearningAndroidAppTheme {
        Surface {
            UserList(userList = userList)
        }
    }
}

@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<User>) {
    if (userList.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.userlist_empty_text),
                style = MaterialTheme.typography.subtitle1
            )
        }
    } else {
        LazyColumn() {
            items(items = userList) { user ->
                UserListItem(user = user)
            }
        }
    }
}

@Composable
fun UserListItem(modifier: Modifier = Modifier, user: User) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /*TODO*/ }
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(56.dp),
            color = MaterialTheme.colors.primary,
            shape = CircleShape,
        ) {
            Text(text = "TODO")
        }
        Text(modifier = Modifier.padding(start = 16.dp), text = user.userName)
    }
}
