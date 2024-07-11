package com.example.practicecompose

import android.util.Range
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateRangeButton(startDate, endDate) {
            openDialog = true
        }

        CalendarDialogComponent(startDate = startDate,
            endDate = endDate,
            openDialog = openDialog,
            onClose = { openDialog = false },
            onSelectRange = { newStartDate, newEndDate ->
                startDate = newStartDate
                endDate = newEndDate
                openDialog = false
            })

        SelectedDates(startDate, endDate)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialogComponent(
    startDate: LocalDate,
    endDate: LocalDate,
    openDialog: Boolean,
    onClose: () -> Unit,
    onSelectRange: (LocalDate, LocalDate) -> Unit
) {
    if (openDialog) {
        CalendarDialog(
            state = rememberUseCaseState(visible = true, onCloseRequest = { onClose.invoke() }),
            config = CalendarConfig(
                yearSelection = true,
                style = CalendarStyle.MONTH,
            ),
            selection = CalendarSelection.Period(
                selectedRange = Range(startDate, endDate), onSelectRange = onSelectRange
            )
        )
    }
}

@Composable
fun DateRangeButton(
    startDate: LocalDate, endDate: LocalDate, onClick: () -> Unit
) {
    Button(
        shape = RectangleShape,
        onClick = onClick,
        modifier = Modifier
            .padding(top = 30.dp)
            .padding(horizontal = 16.dp)
            .height(56.dp),
    ) {
        Text(
            text = buildDateRangeText(startDate, endDate), textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SelectedDates(
    startDate: LocalDate, endDate: LocalDate
) {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            style = TextStyle(fontWeight = FontWeight.Bold),
            text = "Selected Start Date: $startDate"
        )
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "Selected End Date: $endDate",
            style = TextStyle(fontWeight = FontWeight.Bold)

        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCalendarRangeScreen5() {
    CalendarRangeScreen2()
}
