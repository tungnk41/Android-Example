package com.example.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.component.animation.AminateDp
import com.example.component.component.*
import com.example.component.component.multiImageLayout.MultiLayoutImage
import com.example.component.ui.screen.DropDownScreen
import com.example.component.ui.screen.TagEditTextScreen
import com.google.accompanist.insets.ProvideWindowInsets
import java.util.*

class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets {
                Item()
            }
        }
    }
}

@Composable
fun Item() {
//    AminateDp()
//    PickUpImage(onCompleted = {})
//    SwipeRefreshDemo()
//TextSelection()
//    MultiLayoutImage()
//DropDownScreen()
//    TagEditTextScreen()
SearchDebounce()
}

@Preview()
@Composable
fun ItemPreview() {
    Item()
}

