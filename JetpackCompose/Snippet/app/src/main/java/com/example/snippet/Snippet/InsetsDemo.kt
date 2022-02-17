package com.example.snippet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsWithImePadding


@Composable
fun InsetsDemo() {
    val insets = LocalWindowInsets.current
    val imeBottom = with(LocalDensity.current) { insets.ime.bottom.toDp() }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)){
       TextField(value = "abc", onValueChange = {

       })
        Spacer(modifier = Modifier.weight(1f))
//        Box(
//            Modifier
////                .padding(bottom = imeBottom)
//                .background(color = Color.Green)
//                .size(50.dp)
//                .imePadding()
//        )
//        Button(modifier = Modifier.imePadding(), onClick = { /*TODO*/ }) {
//
//        }
//        TextField(modifier = Modifier.imePadding(),value = "abc", onValueChange = {
//
//        })
        TextField(modifier = Modifier.padding(bottom = imeBottom),value = "abc", onValueChange = {

        })

    }
}