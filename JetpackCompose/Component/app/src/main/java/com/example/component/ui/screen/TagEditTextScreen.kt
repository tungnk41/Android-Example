package com.example.component.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.component.component.TagEditText
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun TagEditTextScreen() {
    val initSelectionIndex = 0
    val textValue = remember {
        mutableStateOf(TextFieldValue(
            text = "",
            selection = TextRange(0)
        ))
    }
    val insets = LocalWindowInsets.current
    val imeBottom = with(LocalDensity.current) { insets.ime.bottom.toDp() }
    val listItem = listOf<String>("item 1", "item 2", "item 3", "item 4", "item 5")
    var isTagging by remember { mutableStateOf(false) }
    var tagPosition by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(text = textValue.value.text, color = Color.Black)
        }

        TagEditText(
            textValue = textValue.value,
            isTagging = isTagging,
            menuItems = listItem,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = if (imeBottom != 0.dp) imeBottom else 16.dp),
            onTextFieldChange = {
                textValue.value = it
                val length = textValue.value.text.length

                if (!isTagging) {
                    if (length < 2 && textValue.value.text[length - 1].equals('@')) {
                        isTagging = true
                        tagPosition = length - 1
                    }
                    if (length >= 2 && textValue.value.text.substring(length - 2, length).equals(" @")) {
                        isTagging = true
                        tagPosition = length - 1
                    }
                } else {
                    Log.d("TAG", "TagEditTextScreen: $tagPosition")
                    if (!textValue.value.text[length - 1].isLowerCase()) {
                        isTagging = false
                        tagPosition = 0
                    }
                }
            },
            onItemClick = {
                Log.d("TAG", "TagEditTextScreen: $tagPosition")
                isTagging = false
                val text = textValue.value.text.substring(0, tagPosition) + listItem[it]
                textValue.value = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            }
        )
    }
}