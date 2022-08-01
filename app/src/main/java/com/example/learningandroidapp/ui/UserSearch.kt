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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learningandroidapp.ui.theme.LearningAndroidAppTheme


@Preview
@Composable
fun UserSearchScreenPreview() {
    LearningAndroidAppTheme {
        UserSearchScreen()
    }
}

@Composable
fun UserSearchScreen() {
    val userList = List(12) { "ユーザー$it" }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ユーザー検索",
                        style = MaterialTheme.typography.h6
                    )
                }
            )
        }
    ) {
        Column {
            SearchBox()
            UserList(userList = userList)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(modifier: Modifier = Modifier) {
    // TODO Stateの宣言位置を検討する
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
            label = { Text(text = "ユーザー名") },
            singleLine = true,
            trailingIcon = {
                if (!isEmpty) {
                    IconButton(onClick = { text = "" }) {
                        Icon(
                            Icons.Filled.Cancel,
                            contentDescription = "clear text",
                            Modifier.size(20.dp)
                        )
                    }
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                // キーボードを閉じる
                keyboardController?.hide()
            })
        )
        Button(modifier = Modifier.padding(end = 16.dp), onClick = { /*TODO*/ }) {
            Text(text = "検索")
        }
    }
}

@Preview
@Composable
fun UserListPreview() {
    val userList = List(12) { "ユーザー$it" }
    LearningAndroidAppTheme {
        UserList(userList = userList)
    }
}

@Preview
@Composable
fun EmptyUserListPreview() {
    val userList = List(0) { "ユーザー$it" }
    LearningAndroidAppTheme {
        UserList(userList = userList)
    }
}

@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<String>) {
    // TODO userList引数の型を再検討
    if (userList.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "検索結果なし", style = MaterialTheme.typography.subtitle1)
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(items = userList) { user ->
                UserListItem(user = user)
            }
        }
    }
}

@Composable
fun UserListItem(modifier: Modifier = Modifier, user: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable { /*TODO*/ },
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
        Text(modifier = Modifier.padding(start = 16.dp), text = user)
    }
}
