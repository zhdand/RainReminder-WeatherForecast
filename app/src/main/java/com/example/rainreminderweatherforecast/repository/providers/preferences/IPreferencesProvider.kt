package com.example.rainreminderweatherforecast.repository.providers.preferences

interface IPreferencesProvider{

   fun isUsingCurrentDeviceLocation(): Boolean

   fun isUsingCustomLocation(): Boolean

   fun getCustomLocationCountry():String?

   fun getCustomLocationCityName():String?

   fun getRemindersTimeByDefault(): String?

   fun getRemindersMessage(): String?

   fun setUseCurrentDeviceLocation(value: Boolean)

   fun setUseCustomLocation(value: Boolean)
}