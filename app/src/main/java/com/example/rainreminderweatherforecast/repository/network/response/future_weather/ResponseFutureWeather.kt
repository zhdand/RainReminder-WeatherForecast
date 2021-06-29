package com.example.rainreminderweatherforecast.repository.network.response.future_weather

import com.google.gson.annotations.SerializedName

data class ResponseFutureWeather(
   @SerializedName("city_name")
   val cityName: String,
   @SerializedName("country_code")
   val countryCode: String,
   val data: List<FutureWeatherDataDto>
)