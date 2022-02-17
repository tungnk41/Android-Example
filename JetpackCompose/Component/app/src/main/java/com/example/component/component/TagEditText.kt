package com.example.component.component

import android.text.Editable
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun TagEditText(
    modifier: Modifier = Modifier,
    textValue: TextFieldValue,
    isTagging: Boolean,
    menuItems: List<String>,
    onTextFieldChange: (TextFieldValue) -> Unit,
    onItemClick: (Int) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (editText, boxTagging) = createRefs()

        if(isTagging){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Green)
                .constrainAs(boxTagging) {
                    start.linkTo(editText.start)
                    end.linkTo(editText.end)
                    bottom.linkTo(editText.top)
                }){
                Column {
                    menuItems.forEachIndexed {index,lable->
                        Text(text = lable, modifier = Modifier.clickable {
                            onItemClick(index)
                        })
                    }
                }
            }
        }
        OutlinedTextField(value = textValue,
            onValueChange = {
                onTextFieldChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(editText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

    }
}