package com.example.learningandroidapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
