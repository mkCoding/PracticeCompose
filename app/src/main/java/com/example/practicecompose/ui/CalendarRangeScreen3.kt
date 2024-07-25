package com.example.practicecompose.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun CalendarRangeScreen3() {

    //Initialize start and end dats for calender date selection
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusDays(7)) }
    var openDialog by remember { mutableStateOf(false) }

    DateRangeButton(startDate, endDate) {
        openDialog = true
    }






    if (openDialog) {
        CustomCalendarDialog(
            startDate = startDate,
            endDate = endDate,
            openDialog = openDialog,
            onClose = { openDialog = false },
            onSelectRange = { newStartDate, newEndDate ->
                startDate = newStartDate
                endDate = newEndDate
            }
        )
    }
}

//------------Calendar -----------------
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
                    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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

                    Text(
                        text = "Choose dates",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "Check in",
                                style = TextStyle(color = Color(0xFFFF7918), fontWeight = FontWeight.Bold)
                            )
                            Text(text = startDate.format(DateTimeFormatter.ofPattern("d MMMM, EE")))
                        }

                        val numberOfDays = ChronoUnit.DAYS.between(startDate, endDate).toInt()
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF13C972),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "${numberOfDays}N",
                                style = TextStyle(fontSize = 15.sp, color = Color.White)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Check out",
                                style = TextStyle(color = Color(0xFFFF7918), fontWeight = FontWeight.Bold)
                            )
                            Text(text = endDate.format(DateTimeFormatter.ofPattern("d MMMM, EE")))
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        CustomCalendar(startDate = startDate, endDate = endDate, onSelectRange = onSelectRange)
                    }

                    Button(
                        onClick = onClose,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF7918)),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Submit", color = Color.White)
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

    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous month")
            }
            Text(
                text = currentMonth.month.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next month")
            }
        }

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

        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7

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
                                .padding(top = 9.dp)
                                .padding(0.dp)
                                .clip(getShapeForDate(date, selectedStartDate, selectedEndDate))
                                .background(
                                    color = if (date in selectedStartDate..selectedEndDate) Color(0xFF13C972) else Color.Transparent
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
                            Text(
                                text = day.toString(),
                                color = if (date in selectedStartDate..selectedEndDate) Color.White else Color.Black
                            )
                        }
                        day++
                    }
                }
            }
        }
    }
}

@Composable
fun getShapeForDate(date: LocalDate, startDate: LocalDate, endDate: LocalDate): Shape {
    return when (date) {
        startDate -> RoundedCornerShape(
            topStartPercent = 50,
            bottomStartPercent = 50,
            topEndPercent = 0,
            bottomEndPercent = 0
        )
        endDate -> RoundedCornerShape(
            topEndPercent = 50,
            bottomEndPercent = 50,
            topStartPercent = 0,
            bottomStartPercent = 0
        )
        in startDate.plusDays(1)..endDate.minusDays(1) -> RectangleShape
        else -> RoundedCornerShape(50.dp)
    }
}
//--------Date Range Text & Button--------

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangeButton(
    startDate: LocalDate,
    endDate: LocalDate,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 20.sp,
            text = buildDateRangeText(startDate, endDate),
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            textAlign = TextAlign.Center
        )
    }

}

//Text that should be displayed in Choose Dates
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun buildDateRangeText(startDate: LocalDate?, endDate: LocalDate?): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM")
    return if (startDate != null && endDate != null) {
        "${startDate.format(formatter)} - ${endDate.format(formatter)}"
    } else {
        "Select date range"
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomCalendarDialog() {
    CalendarRangeScreen3()
}