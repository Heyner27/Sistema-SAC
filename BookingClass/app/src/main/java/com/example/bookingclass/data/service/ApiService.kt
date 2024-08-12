package com.example.tenis.data.service

import android.app.usage.UsageEvents
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("year") year: String,
        @Query("month") month: String
    ): List<UsageEvents.Event>
}