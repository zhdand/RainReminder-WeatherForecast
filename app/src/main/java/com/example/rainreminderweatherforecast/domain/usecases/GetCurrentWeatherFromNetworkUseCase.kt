package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.domain.IRepositoryCurrentWeather
import javax.inject.Inject

/**
 * Gets current weather from Network
 */
@FragmentScope
class GetCurrentWeatherFromNetworkUseCase
@Inject
constructor(private val repository: IRepositoryCurrentWeather) {

   suspend operator fun invoke() {
      repository.getCurrentWeather()
   }

}