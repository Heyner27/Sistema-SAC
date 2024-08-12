package com.example.bookingclass.model

import java.time.LocalDate
import java.time.LocalTime


data class Shedule(
    val id: Int,
    val date: LocalDate,
    val time: LocalTime,
    val title: String,
    val category: String
)
