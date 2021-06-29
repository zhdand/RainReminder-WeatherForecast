package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.domain.IRepositoryPreferences
import javax.inject.Inject

class GetPreferencesUseCase
@Inject
constructor(private val repository: IRepositoryPreferences) : IGetPreferencesUseCase {

   override operator fun invoke() = repository.getPreferences()
}
