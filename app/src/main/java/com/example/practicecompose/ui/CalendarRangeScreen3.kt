package com.example.practicecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@Composable
fun CalendarRangeScreen3(){
    val defaultStartDate = LocalDate.now()
    val defaultEndDate = defaultStartDate.plusDays(7)
    CustomCalendarDialog(
        startDate = defaultStartDate,
        endDate = defaultEndDate,
        openDialog = true,
        onClose = {},
        onSelectRange = { _, _ -> }
    )
}

@Composable
fun CustomCalendarDialog(
    startDate: LocalDate,
    endDate: LocalDate,
    openDialog: Boolean,
    onClose: () -> Unit,
    onSelectRange: (LocalDate, LocalDate) -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = onClose) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header with "X" button
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = onClose) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
                            )
                        }
                    }

                    // Custom text at the top of the dialog
                    Text(
                        text = "Choose Dates",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )

                    // Custom calendar implementation
                    CustomCalendar(startDate = startDate, endDate = endDate, onSelectRange = onSelectRange)

                    // Apply button
                    Button(
                        onClick = onClose,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Apply")
                    }
                }
            }
        }
    }
}

@Composable
fun CustomCalendar(
    startDate: LocalDate,
    endDate: LocalDate,
    onSelectRange: (LocalDate, LocalDate) -> Unit
) {
    var selectedStartDate by remember { mutableStateOf(startDate) }
    var selectedEndDate by remember { mutableStateOf(endDate) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Column {
        // Month and year header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous month")
            }
            Text(
                text = currentMonth.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next month")
            }
        }

        // Days of the week header
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Days grid
        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7 // Sunday as the first day

        var day = 1
        for (week in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dow in 0..6) {
                    if (week == 0 && dow < firstDayOfWeek || day > daysInMonth) {
                        Box(modifier = Modifier.weight(1f).padding(4.dp))
                    } else {
                        val date = currentMonth.atDay(day)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(
                                    color = if (date in selectedStartDate..selectedEndDate) Color(0xFF13C972) else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    if (selectedStartDate == selectedEndDate) {
                                        if (date.isBefore(selectedStartDate)) {
                                            selectedStartDate = date
                                        } else {
                                            selectedEndDate = date
                                        }
                                    } else {
                                        selectedStartDate = date
                                        selectedEndDate = date
                                    }
                                    onSelectRange(selectedStartDate, selectedEndDate)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = day.toString(), color = if (date in selectedStartDate..selectedEndDate) Color.White else Color.Black)
                        }
                        day++
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomCalendarDialog() {
    val defaultStartDate = LocalDate.now()
    val defaultEndDate = defaultStartDate.plusDays(7)
    CustomCalendarDialog(
        startDate = defaultStartDate,
        endDate = defaultEndDate,
        openDialog = true,
        onClose = {},
        onSelectRange = { _, _ -> }
    )
}