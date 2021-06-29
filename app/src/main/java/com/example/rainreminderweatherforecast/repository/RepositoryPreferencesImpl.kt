package com.example.rainreminderweatherforecast.repository

import com.example.rainreminderweatherforecast.domain.IRepositoryPreferences
import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider
import javax.inject.Inject

class RepositoryPreferencesImpl
@Inject
constructor(private val preferencesProvider: IPreferencesProvider) : IRepositoryPreferences {

   override fun getPreferences() = preferencesProvider
}
