package com.example.component.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun OtpTextField(length: Int, onTextChange: (String) -> Unit) {
    var textInput by remember {
        mutableStateOf("")
    }

    Box(Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            repeat(length){index->
                OtpField(
                    otp = if(index < textInput.length) textInput[index].toString() else "")
            }
        }
        TextField(
            value = textInput,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .alpha(0f)
                .fillMaxWidth()
                .align(Alignment.Center),
            onValueChange = {
                if(it.length <= length){
                    textInput = it
                    onTextChange(textInput)
                }
            })
    }

}



@Composable
fun OtpField(otp: String) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.Green)
    ) {
        Text(text = otp, modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun OtpTextFieldPreview() {
    OtpTextField(length = 4, {})
}