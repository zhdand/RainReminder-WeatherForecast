package com.example.rainreminderweatherforecast.repository.providers.preferences

class FakePreferencesProvider : IPreferencesProvider {

   private var isUsingCurrentDevicesLocation = true

   override fun isUsingCurrentDeviceLocation(): Boolean {
      return isUsingCurrentDevicesLocation
   }

   override fun isUsingCustomLocation(): Boolean {
      return !isUsingCurrentDevicesLocation
   }

   override fun getCustomLocationCountry(): String? = "US"

   override fun getCustomLocationCityName(): String? = "Miami"

   override fun getRemindersTimeByDefault(): String? = "7:30"

   override fun getRemindersMessage(): String? = "Take an umbrella"

   override fun setUseCurrentDeviceLocation(value: Boolean) {
      isUsingCurrentDevicesLocation = value
   }

   override fun setUseCustomLocation(value: Boolean) {
      isUsingCurrentDevicesLocation = value
   }

}