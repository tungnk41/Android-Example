package com.example.pagingsource

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.Coil
import coil.compose.rememberImagePainter
import com.example.pagingsource.Model.Model
import com.example.pagingsource.ViewModel.MainViewModel
import com.example.pagingsource.ui.theme.PagingSourceTheme
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagingSourceTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ListSourceData(viewModel.data)
                }
            }
        }
    }
}

@Composable
fun ListSourceData(listData: Flow<PagingData<Model>>) {
    val lazyData: LazyPagingItems<Model> = listData.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPagingItems = lazyData) { data ->
            ItemData(model = data!!)
        }
    }
}

@Composable
fun ItemData(model: Model) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(model.image),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = model.name)
    }
}