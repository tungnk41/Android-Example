package com.example.viewpager

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPagerApi
@Composable
fun ViewPager() {
    var pagerState = rememberPagerState(initialPage = 0)
    var isHorizontalLayout = true

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.d("TAG", "HorizontalPage Changed:  $page")
        }
    }

    if (isHorizontalLayout) {
        HorizontalPager(count = 10, state = pagerState, itemSpacing = 10.dp, contentPadding = PaddingValues(10.dp), modifier = Modifier.padding(10.dp)) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop =  1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                // Card content
                Text(
                    text = "Page: $page",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (page.mod(2) == 0) Color.DarkGray else Color.Red)
                )
            }
        }
    } else {
        VerticalPager(count = 10, state = pagerState) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = if (page.mod(2) == 0) Color.DarkGray else Color.Red)
            )
        }
    }


}