package com.example.rainreminderweatherforecast.domain

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.ResultWrapper

interface IRepositoryCurrentWeather {

   fun isNetworkAvailable(): LiveData<ResultNetworkAvailableState>

   val networkResponseResult: LiveData<ResultWrapper>

   suspend fun getCurrentWeather()

   suspend fun saveCurrentWeatherToDatabase(currentWeather: CurrentWeather)

   fun getCurrentWeatherFromDatabase(): LiveData<CurrentWeather>

}