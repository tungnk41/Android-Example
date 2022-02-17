package com.example.component.component

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity


@Composable
fun PopupMenu(
    modifier: Modifier = Modifier,
    menuItems: List<String>,
    isExpanded: Boolean,
    onDismiss: ()->Unit,
    onItemClick: (Int) -> Unit,
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
        modifier = modifier)
    {
        menuItems.forEachIndexed { index, label ->
            DropdownMenuItem(onClick = {
                onDismiss()
                onItemClick(index)
            }) {
                Text(text = label)
            }
        }
    }

}