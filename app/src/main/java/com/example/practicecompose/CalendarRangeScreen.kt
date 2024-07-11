package com.example.practicecompose

import android.util.Range
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
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

@Composable
fun CalendarRangeScreen(){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DateRangePicker()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(){
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusDays(7)) }
    var openDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = buildDateRangeText(startDate, endDate),
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .clickable { openDialog = true }
                .padding(horizontal = 16.dp),
            textStyle = TextStyle(textAlign = TextAlign.Center)
        )

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
fun buildDateRangeText(startDate: LocalDate?, endDate: LocalDate?): String {
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return if (startDate != null && endDate != null) {
        "${startDate.format(formatter)} - ${endDate.format(formatter)}"
    } else {
        "Select date range"
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCalendarRangeScreen(){
    CalendarRangeScreen()
}