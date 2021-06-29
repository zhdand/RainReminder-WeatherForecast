package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider

interface IGetPreferencesUseCase{
   operator fun invoke(): IPreferencesProvider
}