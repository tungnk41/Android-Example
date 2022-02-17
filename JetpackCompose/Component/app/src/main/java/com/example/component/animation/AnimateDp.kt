package com.example.component.animation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AminateDp() {
    var isExpaned by remember {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(
        if(isExpaned) 48.dp else 0.dp
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Blue)) {
        Button(modifier = Modifier
            .padding(bottom = extraPadding),
            onClick = { isExpaned = !isExpaned}) {
            Text(text = "Click")
        }
        Button(modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
            onClick = { isExpaned = !isExpaned }) {
            Text(text = "Click")
            if (isExpaned) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }

    }
}

@Preview
@Composable
fun AminateDpPreview() {
    AminateDp()
}