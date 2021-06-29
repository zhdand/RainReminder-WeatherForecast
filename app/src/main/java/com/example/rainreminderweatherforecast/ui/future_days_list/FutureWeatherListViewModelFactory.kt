package com.example.rainreminderweatherforecast.ui.future_days_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rainreminderweatherforecast.domain.usecases.*
import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FutureWeatherListViewModelFactory(
   private val mApplication: Application,
   private val getFutureWeatherFromNetworkAndSaveToDatabaseUseCase: GetFutureWeatherFromNetworkUseCase,
   private val getFutureWeatherFromDatabaseUseCase: GetFutureWeatherFromDatabaseUseCase,
   private val addAlarmDayReminderToDatabaseUseCase: AddAlarmDayReminderToDatabaseUseCase,
   private val getNetworkResponseFutureWeatherUseCase: GetNetworkResponseFutureWeatherUseCase,
   private val isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
   private val isCityOrCountryEmptyInSettingsUseCase: IsCityOrCountryEmptyInSettingsUseCase,
   private val dispatcher: ICoroutinesDispatchersWrapper,
   private val preferencesUseCase: GetPreferencesUseCase
) : ViewModelProvider.Factory {
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(FutureWeatherListViewModel::class.java))
         return FutureWeatherListViewModel(
            mApplication,
            getFutureWeatherFromNetworkAndSaveToDatabaseUseCase,
            getFutureWeatherFromDatabaseUseCase,
            addAlarmDayReminderToDatabaseUseCase,
            getNetworkResponseFutureWeatherUseCase,
            isNetworkAvailableUseCase,
            isCityOrCountryEmptyInSettingsUseCase,
            dispatcher,
            preferencesUseCase
         ) as T
      throw IllegalArgumentException("Unknown ViewModel")
   }
}