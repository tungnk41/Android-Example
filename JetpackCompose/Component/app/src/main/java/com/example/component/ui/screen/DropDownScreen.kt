package com.example.component.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.component.component.PopupMenu

@Composable
fun DropDownScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val listItem = listOf("Abc", "Abn")
    Column(modifier = Modifier.fillMaxSize()) {

        selectedIndex?.let{
            BasicText(text = listItem[selectedIndex!!])
        }
        Button(onClick = { isExpanded = !isExpanded }) {
            
        }
    }

}