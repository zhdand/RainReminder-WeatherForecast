package com.example.rainreminderweatherforecast.domain.usecases

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsCityOrCountryEmptyInSettingsUseCase
@Inject constructor(private val preferencesUseCase: GetPreferencesUseCase) {

   operator fun invoke(): Boolean {
      return preferencesUseCase().getCustomLocationCityName() == null
            || preferencesUseCase().getCustomLocationCityName()!!.isEmpty()
            || preferencesUseCase().getCustomLocationCountry() == null
            || preferencesUseCase().getCustomLocationCountry()!!.isEmpty()
   }
}