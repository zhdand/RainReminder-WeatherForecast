package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.repository.providers.preferences.FakePreferencesProvider
import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider

class FakeGetPreferencesUseCase: IGetPreferencesUseCase {
   override fun invoke(): IPreferencesProvider = FakePreferencesProvider()
}