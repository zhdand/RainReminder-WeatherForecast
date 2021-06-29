package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.domain.IRepositoryReminders
import javax.inject.Inject

class DeleteAllRemindersRainDaysUseCase
@Inject constructor(
   private val repository: IRepositoryReminders
) {
   suspend operator fun invoke() {
      repository.deleteAllRemindersRainDays()
   }
}