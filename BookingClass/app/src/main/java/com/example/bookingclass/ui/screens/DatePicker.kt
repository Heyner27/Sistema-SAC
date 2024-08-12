package com.example.bookingclass.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookingclass.model.Shedule
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker() {



    var (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val allowedDates = listOf(
        LocalDate.of(2024, 7, 8),
        LocalDate.of(2024, 7, 10),
        LocalDate.of(2024, 7, 12),
        LocalDate.of(2024, 8, 5),
        LocalDate.of(2024, 8, 10),
        LocalDate.of(2024, 8, 15),
        LocalDate.of(2024, 8, 17)

    )
    val events = listOf(
        Shedule(1, LocalDate.of(2024, 7, 8), LocalTime.of(10, 0), "Evento 1", "Avanzado"),
        Shedule(2, LocalDate.of(2024, 7, 10), LocalTime.of(11, 0), "Evento 2", "Intermedio"),
        Shedule(3, LocalDate.of(2024, 7, 10), LocalTime.of(15, 0), "Evento 2", "Principiante"),
        Shedule(4, LocalDate.of(2024, 7, 12), LocalTime.of(9, 0), "Evento 2", "Principiante"),
        Shedule(5, LocalDate.of(2024, 7, 23), LocalTime.of(14, 0), "Evento 3", "Principiante"),
        Shedule(6, LocalDate.of(2024, 8, 5), LocalTime.of(13, 0), "Evento 1", "Avanzado"),
        Shedule(7, LocalDate.of(2024, 8, 10), LocalTime.of(10, 0), "Evento 2", "Intermedio"),
        Shedule(8, LocalDate.of(2024, 8, 15), LocalTime.of(11, 0), "Evento 2", "Intermedio"),
        Shedule(9, LocalDate.of(2024, 8, 15), LocalTime.of(15, 0), "Evento 2", "Avanzado"),
        Shedule(10, LocalDate.of(2024, 8, 15), LocalTime.of(16, 0), "Evento 2", "Principiante"),
        Shedule(11, LocalDate.of(2024, 8, 17), LocalTime.of(9, 0), "Evento 3", "Principiante")
    )



    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Reserva tu clase",
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        CalendarView(
            allowedDates = allowedDates,
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date
            }
        )


        selectedDate?.let { date ->
            Text(
                text = "Selected Date: $date",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
           val filteredEvents = events.filter { it.date == date }.sortedBy { it.time}
               CardList(filteredEvents)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    allowedDates: List<LocalDate>,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val daysInMonth = currentMonth.value.lengthOfMonth()
    val firstDayOfWeek = currentMonth.value.atDay(1).dayOfWeek.value % 7
    val daysOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    currentMonth.value = currentMonth.value.minusMonths(1)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("<", style = MaterialTheme.typography.titleMedium)
            }
            Text(
                text = currentMonth.value.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = {
                    currentMonth.value = currentMonth.value.plusMonths(1)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(">", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for (i in 0..6) {
                Text(
                    text = daysOfWeek.plus(i.toLong()).name.take(1),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        var day = 1
        for (week in 0..5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (weekday in 0..6) {
                    if (week == 0 && weekday < firstDayOfWeek || day > daysInMonth) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                        )
                    } else {
                        val date = currentMonth.value.atDay(day)
                        val isEnabled = allowedDates.contains(date)
                        val isSelected = date == selectedDate
                        val backgroundColor = when {
                            isSelected -> Color.Blue
                            isEnabled -> Color(0xFF7FB3D5 )
                            else -> Color.Transparent
                        }
                        val textColor = when {
                            isSelected -> Color.White
                            isEnabled -> Color.White
                            else -> Color.Gray
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .clip(CircleShape) // Makes the Box circular
                                .background(backgroundColor)
                                .clickable(enabled = isEnabled) { if (isEnabled) onDateSelected(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                color = textColor,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        day++
                    }
                }
            }
        }
    }
}



