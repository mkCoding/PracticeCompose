package com.example.practicecompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckboxGrid() {
    // Remember the state of the checkboxes
    val column1Checkboxes = remember { mutableStateListOf(*Array(5) { mutableStateOf(false) }) }
    val column2Checkboxes = remember { mutableStateListOf(*Array(4) { mutableStateOf(false) }) }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.weight(1f),
            content = {
                items(column1Checkboxes.size) { index ->
                    CheckboxWithLabel(
                        isChecked = column1Checkboxes[index].value,
                        onCheckedChange = { column1Checkboxes[index].value = it }
                    )
                }
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.weight(1f),
            content = {
                items(column2Checkboxes.size) { index ->
                    CheckboxWithLabel(
                        isChecked = column2Checkboxes[index].value,
                        onCheckedChange = { column2Checkboxes[index].value = it }
                    )
                }
            }
        )
    }
}

@Composable
fun CheckboxWithLabel(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (isChecked) "Checked" else "Unchecked", fontSize = 12.sp)
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxGrid() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CheckboxGrid()
    }

}