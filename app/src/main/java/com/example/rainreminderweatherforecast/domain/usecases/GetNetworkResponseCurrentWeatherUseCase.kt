package com.example.rainreminderweatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.domain.IRepositoryCurrentWeather
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import javax.inject.Inject

@FragmentScope
class GetNetworkResponseCurrentWeatherUseCase
@Inject
constructor(
   private val repository: IRepositoryCurrentWeather
){
   operator fun invoke(): LiveData<ResultWrapper>{
     return repository.networkResponseResult
   }
}