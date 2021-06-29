package com.example.rainreminderweatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.domain.IRepositoryCurrentWeather
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsNetworkAvailableUseCase
@Inject constructor(
   private val repository: IRepositoryCurrentWeather
) {

   operator fun invoke(): LiveData<ResultNetworkAvailableState> {
      return repository.isNetworkAvailable()
   }
}
