package com.example.rainreminderweatherforecast.repository.network.response.current_weather

data class CurrentWeatherDto(
    val code: Int,
    val description: String,
    val icon: String
)