package com.example.component.component

import android.view.textclassifier.TextSelection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TextSelection() {
    SelectionContainer() {
        Column() {
            Text(text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            Text(text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            DisableSelection {
                Text(text = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
            }
            Text(text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        }
    }
}