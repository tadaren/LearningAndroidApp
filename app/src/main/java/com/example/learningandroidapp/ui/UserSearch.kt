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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.learningandroidapp.R
import com.example.learningandroidapp.models.User
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme
import com.example.learningandroidapp.viewmodel.UserSearchUiState
import com.example.learningandroidapp.viewmodel.UserSearchViewModel


@Preview(showSystemUi = true)
@Composable
private fun UserSearchScreenPreview() {
    LearningAndroidAppTheme {
        UserSearchScreen(onNavigate = {})
    }
}

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel = viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigate: (String) -> Unit
) {
    val uiState = viewModel.uiState
    val onUserNameChanged = { userName: String ->
        viewModel.onUserNameChanged(userName)
    }
    val searchUser = {
        viewModel.searchUser()
    }

    if (uiState.hasError) {
        val context = LocalContext.current
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(R.string.network_error_message)
            )
            viewModel.errorMessageShown()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
            SearchBox(
                userName = uiState.userName,
                onTextChanged = onUserNameChanged,
                onSearch = searchUser
            )
            UserSearchContent(uiState = uiState, onNavigate = onNavigate)
        }
    }
}

@Composable
private fun UserSearchContent(uiState: UserSearchUiState, onNavigate: (String) -> Unit) {
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        UserList(userList = uiState.userList, onNavigate = onNavigate)
    }
}

@Preview
@Composable
private fun UserSearchContentLoadingPreview() {
    LearningAndroidAppTheme {
        Surface {
            UserSearchContent(uiState = UserSearchUiState(isLoading = true), onNavigate = {})
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    userName: String,
    onTextChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            value = userName,
            onValueChange = onTextChanged,
            label = { Text(text = stringResource(R.string.searchbox_label)) },
            singleLine = true,
            trailingIcon = {
                if (userName.isNotEmpty()) {
                    IconButton(onClick = { onTextChanged("") }) {
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
        Button(modifier = Modifier.padding(end = 16.dp), onClick = onSearch) {
            Text(text = stringResource(R.string.searchbox_button_text))
        }
    }
}

@Preview
@Composable
private fun UserListPreview() {
    val userList = List(12) {
        User(userName = "ユーザー$it", avatarUrl = "")
    }
    LearningAndroidAppTheme {
        Surface {
            UserList(userList = userList, onNavigate = {})
        }
    }
}

@Preview
@Composable
private fun EmptyUserListPreview() {
    val userList = List(0) {
        User(userName = "ユーザー$it", avatarUrl = "")
    }
    LearningAndroidAppTheme {
        Surface {
            UserList(userList = userList, onNavigate = {})
        }
    }
}

@Composable
private fun UserList(
    modifier: Modifier = Modifier,
    userList: List<User>,
    onNavigate: (String) -> Unit
) {
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
                UserListItem(user = user, onClick = { onNavigate(user.userName) })
            }
        }
    }
}

@Composable
private fun UserListItem(modifier: Modifier = Modifier, user: User, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = "avatar",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(56.dp)
                .clip(CircleShape)
        )
        Text(modifier = Modifier.padding(start = 16.dp), text = user.userName)
    }
}
