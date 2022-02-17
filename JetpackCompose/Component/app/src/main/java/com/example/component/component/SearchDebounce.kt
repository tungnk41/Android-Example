package com.example.component.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

fun search(text: String){
    Log.d(TAG, "search: " + text)
}

@Composable
fun SearchDebounce() {
    var searchText by remember { mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null)}
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)){
        OutlinedTextField(value = searchText, onValueChange = {
            searchText = it
            job?.cancel()
            job = coroutineScope.launch {
                delay(300L)
                search(it)
                job = null
            }
        })
    }
}