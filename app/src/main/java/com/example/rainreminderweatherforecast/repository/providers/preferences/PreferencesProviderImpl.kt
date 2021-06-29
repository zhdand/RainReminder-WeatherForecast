package com.example.rainreminderweatherforecast.repository.providers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject

class PreferencesProviderImpl
@Inject constructor(private val context: Context) : IPreferencesProvider {

   private val preferences: SharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(this.context)

   override fun isUsingCurrentDeviceLocation(): Boolean {
      return preferences.getBoolean("use_current_location", false)
   }

   override fun isUsingCustomLocation(): Boolean {
      return preferences.getBoolean("use_custom_location", false)
   }

   override fun getCustomLocationCountry(): String? {
      return preferences.getString("country", "")
   }

   override fun getCustomLocationCityName(): String? {
      return preferences.getString("city_name", "")
   }

   override fun getRemindersTimeByDefault(): String? {
      return preferences.getString("reminder_time_by_default", "")
   }

   override fun getRemindersMessage(): String? {
      return preferences.getString("message_for_reminder", "")
   }

   override fun setUseCurrentDeviceLocation(value: Boolean) {
      preferences.edit().putBoolean("use_custom_location", value).apply()
   }

   override fun setUseCustomLocation(value: Boolean) {
      preferences.edit().putBoolean("use_current_location", value).apply()
   }

}