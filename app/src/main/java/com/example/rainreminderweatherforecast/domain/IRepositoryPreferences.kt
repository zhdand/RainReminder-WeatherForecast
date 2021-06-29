package com.example.rainreminderweatherforecast.domain

import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider

interface IRepositoryPreferences {

   fun getPreferences(): IPreferencesProvider
}
