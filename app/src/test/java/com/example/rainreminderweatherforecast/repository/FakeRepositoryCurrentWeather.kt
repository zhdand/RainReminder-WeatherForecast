package com.example.rainreminderweatherforecast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.rainreminderweatherforecast.domain.IRepositoryCurrentWeather
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.ResultWrapper

class FakeRepositoryCurrentWeather : IRepositoryCurrentWeather {

   private val currentWeatherLiveData = MutableLiveData<CurrentWeather>()

   override fun isNetworkAvailable(): LiveData<ResultNetworkAvailableState> {
      return liveData {
         emit(ResultNetworkAvailableState.NetworkAvailable)
      }
   }

   override val networkResponseResult: MutableLiveData<ResultWrapper> =
      MutableLiveData(ResultWrapper.Success)
//      get() = MutableLiveData ResultWrapper.Success() }

   override suspend fun getCurrentWeather() {
      networkResponseResult.value = ResultWrapper.Success
   }

   override suspend fun saveCurrentWeatherToDatabase(currentWeather: CurrentWeather) {

   }

   override fun getCurrentWeatherFromDatabase(): LiveData<CurrentWeather> {
      currentWeatherLiveData.value = currentWeather
      return currentWeatherLiveData
   }

   private val currentWeather = CurrentWeather(
      1, "03.06.2021", "Dnepr", 15.0,
      5.0, 3.0, "icon", "now", "Sunny"
   )
}