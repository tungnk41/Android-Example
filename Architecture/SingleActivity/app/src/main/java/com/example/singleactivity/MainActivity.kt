package com.example.singleactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.singleactivity.ui.router.Router
import com.example.singleactivity.ui.theme.SingleActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SingleActivityTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Router()
                }
            }
        }
    }
}
