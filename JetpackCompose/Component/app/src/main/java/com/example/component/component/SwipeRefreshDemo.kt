package com.example.component.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun SwipeRefreshDemo() {
    var isRefreshing by remember { mutableStateOf(false)}
    LaunchedEffect(key1 = isRefreshing, block = {
        if(isRefreshing){
            delay(1000)
            isRefreshing = false
        }
    })

    Column(Modifier.fillMaxSize()) {
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = { isRefreshing = true}){
            LazyColumn {
                items(30) { index ->
                    Text("$isRefreshing", modifier = Modifier.fillMaxWidth().height(30.dp))
                }
            }

        }
    }

}