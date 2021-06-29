package com.example.rainreminderweatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.domain.IRepositoryFutureWeather
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import javax.inject.Inject

@FragmentScope
class GetFutureWeatherFromDatabaseUseCase
@Inject constructor(
   private val repository: IRepositoryFutureWeather
) {

   operator fun invoke(): LiveData<List<FutureWeather>> {
      return repository.getFutureWeatherFromDatabase()
   }
}