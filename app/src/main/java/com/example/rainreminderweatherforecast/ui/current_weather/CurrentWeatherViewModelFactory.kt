package com.example.rainreminderweatherforecast.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rainreminderweatherforecast.domain.usecases.*
import com.example.rainreminderweatherforecast.domain.usecases.IGetPreferencesUseCase
import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CurrentWeatherViewModelFactory
@Inject constructor(
   private val getCurrentWeatherFromNetworkUseCase: GetCurrentWeatherFromNetworkUseCase,
   private val getCurrentWeatherFromDatabaseUseCase: GetCurrentWeatherFromDatabaseUseCase,
   private val getNetworkResponseCurrentWeatherUseCase: GetNetworkResponseCurrentWeatherUseCase,
   private val isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
   private val preferencesUseCase: IGetPreferencesUseCase,
   private val isCityOrCountryEmptyInSettingsUseCase: IsCityOrCountryEmptyInSettingsUseCase,
   private val dispatcher: ICoroutinesDispatchersWrapper,
) : ViewModelProvider.Factory {
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
         return CurrentWeatherViewModel(
            getCurrentWeatherFromNetworkUseCase,
            getCurrentWeatherFromDatabaseUseCase,
            getNetworkResponseCurrentWeatherUseCase,
            isNetworkAvailableUseCase,
            preferencesUseCase,
            isCityOrCountryEmptyInSettingsUseCase,
            dispatcher
         ) as T
      }
      throw  IllegalArgumentException("Unknown ViewModel class")
   }
}