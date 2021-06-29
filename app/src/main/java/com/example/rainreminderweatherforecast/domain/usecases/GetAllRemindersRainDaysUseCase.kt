package com.example.rainreminderweatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.domain.IRepositoryReminders
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import javax.inject.Inject

@FragmentScope
class GetAllRemindersRainDaysUseCase
@Inject constructor(private val repository: IRepositoryReminders) {

   operator fun invoke(): LiveData<List<ReminderRainDay>> {
      return repository.getAllAlarmDayReminder()
   }
}