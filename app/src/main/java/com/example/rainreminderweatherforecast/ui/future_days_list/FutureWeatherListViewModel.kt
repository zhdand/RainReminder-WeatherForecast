package com.example.rainreminderweatherforecast.ui.future_days_list

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rainreminderweatherforecast.domain.usecases.GetNetworkResponseFutureWeatherUseCase
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.broadcastreceiver.MyAlarmReceiver
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import com.example.rainreminderweatherforecast.domain.usecases.*
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import com.example.rainreminderweatherforecast.utils.Converter
import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class FutureWeatherListViewModel
@Inject constructor(
   private val mApplication: Application,
   private val getFutureWeatherFromNetworkUseCase: GetFutureWeatherFromNetworkUseCase,
   getFutureWeatherFromDatabaseUseCase: GetFutureWeatherFromDatabaseUseCase,
   private val addAlarmDayReminderToDatabaseUseCase: AddAlarmDayReminderToDatabaseUseCase,
   getNetworkResponseFutureWeatherUseCase: GetNetworkResponseFutureWeatherUseCase,
   isNetworkAvailableUseCase: IsNetworkAvailableUseCase,
   private val isCityOrCountryEmptyInSettingsUseCase: IsCityOrCountryEmptyInSettingsUseCase,
   private val dispatcher: ICoroutinesDispatchersWrapper,
   preferencesUseCase: GetPreferencesUseCase
) : AndroidViewModel(mApplication) {

   var isNetworkAvailable: LiveData<ResultNetworkAvailableState> = isNetworkAvailableUseCase()

   var futureWeatherFromDatabase: LiveData<List<FutureWeather>> =
      getFutureWeatherFromDatabaseUseCase()

   val networkResponseResult: LiveData<ResultWrapper> = getNetworkResponseFutureWeatherUseCase()

   private val _isCityCountryEmptyInSettings = MutableLiveData<ResultWrapper>()
   val isCityCountryEmptyInSettings: LiveData<ResultWrapper>
      get() = _isCityCountryEmptyInSettings

   var preferences = preferencesUseCase()

   /**
    * Retrieves the future weather from Network,
    * depending on whether the current device location is enabled
    * or the location is set manually in the  settings.
    */
   fun getFutureWeather() {
      viewModelScope.launch(dispatcher.io) {
         when {
            preferences.isUsingCurrentDeviceLocation() ->
               getFutureWeatherFromNetworkUseCase()

            preferences.isUsingCustomLocation() -> {
               if (isCityOrCountryEmptyInSettingsUseCase()) {
                  _isCityCountryEmptyInSettings.postValue(
                     ResultWrapper.Error(R.string.message_error_empty_city_in_settings)
                  )
                  return@launch
               } else
                  getFutureWeatherFromNetworkUseCase()
            }
         }
      }
   }

   /**
    * Saves reminder in the database and sets an alarm
    */
   fun saveAlarm(reminderRainDay: ReminderRainDay) {
      viewModelScope.launch(dispatcher.io) {
         addAlarmDayReminderToDatabaseUseCase(reminderRainDay)
      }
      setAlarm(reminderRainDay)
   }

   /**
    * Sets alarm at specific time
    */
   private fun setAlarm(reminderRainDay: ReminderRainDay) {
      val alarmManager = mApplication.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(mApplication, MyAlarmReceiver::class.java)
      intent.putExtra("EXTRA_ALARM_ID", reminderRainDay.id)
      val pendingIntent = PendingIntent.getBroadcast(mApplication, reminderRainDay.id, intent, 0)

      val dateInMilliseconds: Long = Converter.convertDateTimeToMilliseconds(reminderRainDay)

      alarmManager.setExact(
         AlarmManager.RTC_WAKEUP,
         dateInMilliseconds,
         pendingIntent
      )
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