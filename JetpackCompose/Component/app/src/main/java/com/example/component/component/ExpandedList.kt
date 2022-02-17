package com.example.component.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.component.Mockup.MockupListObjectData
import com.example.component.model.ObjectDataModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ExpandedList() {
    var expanded by remember { mutableStateOf(false) }

    var listData: List<ObjectDataModel> = MockupListObjectData.data

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if(!expanded){
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }) {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = listData.get(0).name,
                        modifier = Modifier.fillMaxWidth(0.92F),
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(
                        Icons.Default.ArrowDropDown, "",
                    )
                    Divider()
                }
            }
        }else{
//            AnimatedVisibility(
//                visible = expanded,
//
//            ) {
//                Column() {
//                    LazyColumn(Modifier.weight(0.7f)) {
//                        items(listData) {
//                            Text(
//                                text = it.name,
//                                modifier = Modifier.fillMaxWidth(),
//                                style = MaterialTheme.typography.subtitle1
//                            )
//                            Spacer(modifier = Modifier.height(10.dp))
//                            Divider()
//                        }
//
//                    }
//
//                    Icon(
//                        Icons.Default.ArrowDropDown, "",
//                        modifier = Modifier
//                            .weight(0.1f)
//                            .clickable { expanded = !expanded }
//                    )
//                }
//            }

            Column() {
                LazyColumn(Modifier.weight(0.7f)) {
                    items(listData) {
                        Text(
                            text = it.name,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider()
                    }

                }

                Icon(
                    Icons.Default.ArrowDropDown, "",
                    modifier = Modifier
                        .weight(0.1f)
                        .clickable { expanded = !expanded }
                )
            }

        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview
@Composable
fun ExpandedListPreview() {
    ExpandedList()
}

//Column(modifier = Modifier.fillMaxWidth()) {
//    listData.forEach {
//        Text(
//            text = it.name,
//            modifier = Modifier.fillMaxWidth(),
//            style = MaterialTheme.typography.subtitle1
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Divider()
//    }
//    Spacer(modifier = Modifier.height(10.dp))
//    Icon(
//        Icons.Default.ArrowDropDown, "",
//        modifier = Modifier.clickable { expanded = !expanded }
//    )
//}