package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(modifier: Modifier = Modifier) {
    // TODO Stateの宣言位置を検討する
    var text by remember {
        mutableStateOf("")
    }
    val isEmpty by remember {
        derivedStateOf { text.isEmpty() }
    }
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
            }
        )
        Button(modifier = Modifier.padding(end = 16.dp), onClick = { /*TODO*/ }) {
            Text(text = "検索")
        }
    }
}

@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<String>) {
    // TODO userList引数の型を再検討
    LazyColumn {
        items(items = userList) { user ->
            UserListItem(user = user)
        }
    }
}

@Composable
fun UserListItem(modifier: Modifier = Modifier, user: String) {
    Row(
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
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
