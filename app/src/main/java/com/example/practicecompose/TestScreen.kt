//package com.example.practicecompose
//
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.DatePicker
//import androidx.compose.material3.DatePickerState
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberDatePickerState
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.tooling.preview.Preview
//import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
//import com.maxkeppeler.sheets.calendar.CalendarDialog
//import com.maxkeppeler.sheets.calendar.models.CalendarConfig
//import com.maxkeppeler.sheets.calendar.models.CalendarSelection
//import com.maxkeppeler.sheets.calendar.models.CalendarStyle
//
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//@Composable
//fun TestScreen() {
//    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
//    var showDatePicker by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Hello",
//            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Button(
//            onClick = {
//                showDatePicker = true
//            }
//        ) {
//            Text("Open Date Picker")
//        }
//
//        if (showDatePicker) {
//            BackHandler(onBack = { showDatePicker = false })
//            CustomDatePicker(
//                onClose = { showDatePicker = false },
//                selectedDate = selectedDate
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Selected Date: ${selectedDate.value}")
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CustomDatePicker(
//    onClose: () -> Unit,
//    selectedDate: MutableState<LocalDate>
//) {
//    AlertDialog(
//        onDismissRequest = onClose,
//        title = { Text("Select a date") },
//        confirmButton = {
//            Button(
//                onClick = onClose // Optionally validate dates or perform other actions
//            ) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            Button(
//                onClick = onClose
//            ) {
//                Text("Cancel")
//            }
//        },
//        text = {
//            Column {
//                CalendarDialog(
//                    state = rememberUseCaseState(visible = true, true, onCloseRequest = onClose),
//                    config = CalendarConfig(
//                        yearSelection = true,
//                        style = CalendarStyle.MONTH,
//                    ),
//                    selection = CalendarSelection.Date(
//                        selectedDate = selectedDate.value, // Provide the current selected date
//                        onDateSelected = { newDate ->
//                            selectedDate.value = newDate
//                        }
//                    )
//                )
//            }
//        }
//    )
//}
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewTestScreen() {
//    TestScreen()
//}
//
//
//
//
//@Preview
//@Composable
//fun PreviewCustomDatePicker(){
//    CustomDatePicker()
//}