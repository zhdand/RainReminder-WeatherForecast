package com.example.rainreminderweatherforecast.repository.network.response.current_weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDataDto(
   @SerializedName("city_name")
   val cityName: String,
   @SerializedName("country_code")
   val countryCode: String,
   val clouds: Int,
   val datetime: String,
   @SerializedName("precip")
   val precipitation: Double,
   @SerializedName("temp")
   val temperature: Double,
   val ts: Int,
   @SerializedName("wind_spd")
   val windSpeed: Double,
   val lat: Double,
   val lon: Double,
   @SerializedName("state_code")
   val stateCode: Int,
   val timezone: String,
   @SerializedName("weather")
   val currentWeather: CurrentWeatherDto
)

