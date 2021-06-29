package com.example.rainreminderweatherforecast.ui.current_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.domain.usecases.*
import com.example.rainreminderweatherforecast.domain.usecases.IGetPreferencesUseCase
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentWeatherViewModel
@Inject constructor(
   private val getCurrentWeatherFromNetworkUseCase: GetCurrentWeatherFromNetworkUseCase,
   getCurrentWeatherFromDatabaseUseCase: GetCurrentWeatherFromDatabaseUseCase,
   getNetworkResponseCurrentWeatherUseCase: GetNetworkResponseCurrentWeatherUseCase,
   isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
   preferencesUseCase: IGetPreferencesUseCase,
   private val isCityOrCountryEmptyInSettingsUseCase: IsCityOrCountryEmptyInSettingsUseCase,
   private val dispatcher: ICoroutinesDispatchersWrapper,
) : ViewModel() {

   var isNetworkAvailable: LiveData<ResultNetworkAvailableState>

   var currentWeatherFromDatabase: LiveData<CurrentWeather>

   val networkResponseResult: LiveData<ResultWrapper>

   private val _isCityCountryEmptyInSettings = MutableLiveData<ResultWrapper>()
   val isCityCountryEmptyInSettings: LiveData<ResultWrapper>
      get() = _isCityCountryEmptyInSettings

   val preferences = preferencesUseCase()

   init {
      isNetworkAvailable = isNetworkAvailableUseCase()

      currentWeatherFromDatabase = getCurrentWeatherFromDatabaseUseCase()

      networkResponseResult = getNetworkResponseCurrentWeatherUseCase()
   }

   /**
    * Retrieves the current weather from Network,
    * depending on whether the current device location is enabled
    * or the location is set manually in the  settings.
    */
   fun getCurrentWeather() {
      viewModelScope.launch(dispatcher.io) {
         when {
            preferences.isUsingCurrentDeviceLocation() ->
               getCurrentWeatherFromNetworkUseCase()

            preferences.isUsingCustomLocation() -> {
               if (isCityOrCountryEmptyInSettingsUseCase()) {
                  _isCityCountryEmptyInSettings.postValue(
                     ResultWrapper.Error(R.string.message_error_empty_city_in_settings)
                  )
                  return@launch
               } else
                  getCurrentWeatherFromNetworkUseCase()
            }
         }
      }
   }

   /**
    * Sets the use of the devices current location in the settings.
    */
   fun setPreferenceUseCurrentDeviceLocation(value: Boolean) {
      preferences.setUseCurrentDeviceLocation(value)
   }

   /**
    * Sets the use of the custom location in the settings.
    */
   fun setPreferenceUseCustomLocation(value: Boolean) {
      preferences.setUseCustomLocation(value)
   }

}