package com.example.rainreminderweatherforecast.domain.usecases

import com.example.rainreminderweatherforecast.domain.IRepositoryReminders
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import javax.inject.Inject

class AddAlarmDayReminderToDatabaseUseCase
@Inject constructor(
   private val repository: IRepositoryReminders
) {

   suspend operator fun invoke(reminderRainDay: ReminderRainDay) {
      repository.saveAlarm(reminderRainDay)
   }

}
