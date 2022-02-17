package com.example.component.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(text: String, gradient: Brush, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(gradient)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },)
        {
            Text(text)
        }
}

@Preview
@Composable
fun GradientButtonPreview() {
    GradientButton(
        text = "Button",
        gradient = Brush.horizontalGradient(colors = listOf(Color.Green, Color.Magenta))
    ) {

    }
}