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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class CheckboxItem(
    val name: String,
    var isChecked: MutableState<Boolean>
)

@Composable
fun CheckboxGrid2() {
    // Initialize the state of the checkboxes with names
    val column1Checkboxes = remember {
        mutableStateListOf(
            CheckboxItem("Item 1", mutableStateOf(false)),
            CheckboxItem("Item 2", mutableStateOf(false)),
            CheckboxItem("Item 3", mutableStateOf(false)),
            CheckboxItem("Item 4", mutableStateOf(false)),
            CheckboxItem("Item 5", mutableStateOf(false))
        )
    }

    val column2Checkboxes = remember {
        mutableStateListOf(
            CheckboxItem("Item 6", mutableStateOf(false)),
            CheckboxItem("Item 7", mutableStateOf(false)),
            CheckboxItem("Item 8", mutableStateOf(false)),
            CheckboxItem("Item 9", mutableStateOf(false))
        )
    }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            column1Checkboxes.forEach { item ->
                CheckboxWithLabel(item = item)
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            column2Checkboxes.forEach { item ->
                CheckboxWithLabel(item = item)
            }
        }
    }
}

@Composable
fun CheckboxWithLabel(item: CheckboxItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Checkbox(
            checked = item.isChecked.value,
            onCheckedChange = { item.isChecked.value = it }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item.name, fontSize = 12.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCheckBox2Grid() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CheckboxGrid2()
    }

}