package com.example.practicecompose

import android.util.Range
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarRangeScreen2() {
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusDays(7)) }
    var openDialog by remember { mutableStateOf(false) }

    //initialize start and end date when composable is first created
//    LaunchedEffect(true) {
//        startDate = null
//        endDate = null
//    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = { openDialog = true },
            modifier = Modifier
                .padding(horizontal = 16.dp) // Example padding
                .height(56.dp), // Example height
        ) {
            Text(
                text = buildDateRangeText(startDate, endDate),
                textAlign = TextAlign.Center // Center aligns the text
            )
        }

        if (openDialog) {
            CalendarDialog(
                state = rememberUseCaseState(visible = true, onCloseRequest = { openDialog = false }),
                config = CalendarConfig(
                    yearSelection = true,
                    style = CalendarStyle.MONTH,
                ),
                selection = CalendarSelection.Period(
                    selectedRange = Range(startDate, endDate),
                    onSelectRange = { newStartDate, newEndDate ->
                        startDate = newStartDate
                        endDate = newEndDate
                        openDialog = false
                    }
                )
            )
        }

        Text("Selected Start Date: $startDate")
        Text("Selected End Date: $endDate")
    }
}

@Composable
fun buildDateRangeText(startDate: LocalDate, endDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return "${startDate.format(formatter)} - ${endDate.format(formatter)}"
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarRangeScreen5() {
    CalendarRangeScreen2()
}
