package com.example.rainreminderweatherforecast.ui.models

data class CurrentWeatherUI(
   val cityName: String,
   val temperature: String,
   val wind: String,
   val precipitation: String,
   val image: String,
   val description: String
)
