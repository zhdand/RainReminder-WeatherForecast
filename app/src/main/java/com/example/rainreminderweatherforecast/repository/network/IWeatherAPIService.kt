package com.example.rainreminderweatherforecast.repository.network

import com.example.rainreminderweatherforecast.BuildConfig
import com.example.rainreminderweatherforecast.repository.network.response.current_weather.ResponseCurrentWeather
import com.example.rainreminderweatherforecast.repository.network.response.future_weather.ResponseFutureWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherAPIService {

   //   https://api.weatherbit.io/v2.0/current?city=...&country=...&key=
   @GET("current?")
   suspend fun getCurrentWeather(
      @Query("city") city: String?,
      @Query("country") country: String?,
      @Query("lang") language: String
   ): Response<ResponseCurrentWeather>

   //https://api.weatherbit.io/v2.0/current?lat=...&lon=...
   @GET("current")
   suspend fun getCurrentWeather(
      @Query("lat") latitude: Double,
      @Query("lon") longitude:Double,
      @Query("lang") language: String
   ): Response<ResponseCurrentWeather>

   //https://api.weatherbit.io/v2.0/forecast/daily?city=...&country=...&key=
   @GET("forecast/daily?")
   suspend fun getFutureWeather(
      @Query("city") city: String?,
      @Query("country") country: String?,
      @Query("lang") language: String
   ): Response<ResponseFutureWeather>

   @GET("forecast/daily?")
   suspend fun getFutureWeather(
      @Query("lat") latitude: Double,
      @Query("lon") longitude:Double,
      @Query("lang") language: String
   ): Response<ResponseFutureWeather>

   companion object {
      const val API_BASE_URL = "https://api.weatherbit.io/v2.0/"
      const val API_KEY = BuildConfig.API_KEY
      //      https://www.weatherbit.io/static/img/icons/c02d.png
      const val URL_ICON = "https://www.weatherbit.io/static/img/icons/"
   }
}
