package com.example.snippet

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun SideEffect(viewModel: MainViewModel = MainViewModel()) {

    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var stateClicked by remember {
        mutableStateOf(0)
    }

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                onBackPressed()
            }
        }
    }

    //Run when recompose
    SideEffect {
        var sum = 0;
        for (i in 0..10 step 1){
            sum += i
        }
        Log.d("TAG", "SideEffect: $sum" )
    }

    // dispose/relaunch if dispatcher changes
    DisposableEffect(key1 = dispatcher, effect = {
        dispatcher?.addCallback(backCallback)
        Log.d("TAG", "DisposableEffect: dispatcher" )

        //Release variable to prevent mem leak
        onDispose {
            backCallback.remove()
        }
    })

    // //Run when state Changed
    DisposableEffect(key1 = stateClicked, effect = {
        Log.d("TAG", "DisposableEffect: stateClicked" )

        onDispose {
        }
    })

    //Run when state Changed
    LaunchedEffect(key1 = stateClicked, block = {
        delay(1000);
        Log.d("TAG", "LaunchedEffect: " )
    })

    Button(onClick = {
        stateClicked++
    }) {
        Text(text = "Click $stateClicked")
    }
}

@Preview(showBackground = true)
@Composable
fun SideEffectPreview() {
    SideEffect()

}