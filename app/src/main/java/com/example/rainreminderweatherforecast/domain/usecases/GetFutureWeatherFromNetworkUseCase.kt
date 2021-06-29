package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.domain.IRepositoryFutureWeather
import javax.inject.Inject

@FragmentScope
class GetFutureWeatherFromNetworkUseCase
@Inject constructor(
   private val repository: IRepositoryFutureWeather
) {

   suspend operator fun invoke(){
      repository.getFutureWeatherFromNetwork()
   }

}