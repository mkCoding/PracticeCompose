package com.example.practicecompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
Dropdown menu in Compose (Gender)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen3() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Make a selection",
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.padding(16.dp)
        )

        GenderDropdown()
        NumberDropdown()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(){
    //dropdown state
    var isExpanded by remember { mutableStateOf(false) } //boolean
    var selectedGender by remember { mutableStateOf<String?>(null) } // Use nullable String

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 70.dp)
            .padding(bottom = 70.dp)

    ) {

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
        ) {
            TextField(
                value = selectedGender
                    ?: "Select gender", // Display placeholder text if no gender selected
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }

            ) {
                DropdownMenuItem(
                    text = { Text(text = "Male") },
                    onClick = {
                        selectedGender = "Male"
                        isExpanded = false
                    }

                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Female")
                    },
                    onClick = {
                        selectedGender = "Female"
                        isExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Other")
                    },
                    onClick = {
                        selectedGender = "Other"
                        isExpanded = false
                    }

                )
            }
        }





    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberDropdown() {

    val numbers = (1..10).toList()
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
        ) {
            TextField(
                value = selectedNumber?.toString() ?: "Select number", // Display placeholder text if no number selected
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                numbers.forEach { number ->
                    DropdownMenuItem(
                        text = { Text(text = number.toString()) },
                        onClick = {
                            selectedNumber = number
                            isExpanded = false
                        }
                    )
                }

            }

        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewDropdownScreen(){
    TestScreen3()
}