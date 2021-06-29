package com.example.rainreminderweatherforecast.repository.network.response.future_weather

import com.google.gson.annotations.SerializedName

data class FutureWeatherDataDto(
   val clouds: Int,
   val datetime: String,
   @SerializedName("precip")
   val precipitation: Double,
   @SerializedName("temp")
   val temperature: Double,
   val ts: Int,
   @SerializedName("wind_speed")
   val windSpeed: Double,
   @SerializedName("weather")
   val futureWeather: FutureWeatherDto
)

