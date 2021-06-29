package com.example.rainreminderweatherforecast.domain

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.ResultWrapper

interface IRepositoryFutureWeather {

   val networkResponseResult: LiveData<ResultWrapper>

   fun isNetworkAvailable(): LiveData<ResultNetworkAvailableState>

   suspend fun getFutureWeatherFromNetwork()

   suspend fun saveFutureWeatherToDatabase(futureWeather: List<FutureWeather>)

   fun getFutureWeatherFromDatabase(): LiveData<List<FutureWeather>>

}