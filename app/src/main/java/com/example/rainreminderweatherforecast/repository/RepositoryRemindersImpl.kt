package com.example.rainreminderweatherforecast.repository

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.domain.IRepositoryReminders
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import com.example.rainreminderweatherforecast.repository.db.IReminderRainDaysDao
import javax.inject.Inject

class RepositoryRemindersImpl
@Inject constructor(private val remindersRainDaysDao: IReminderRainDaysDao) : IRepositoryReminders {

   override suspend fun saveAlarm(reminderRainDay: ReminderRainDay) {
      remindersRainDaysDao.updateOrInsertAlarm(reminderRainDay)
   }

   override fun getAllAlarmDayReminder(): LiveData<List<ReminderRainDay>> {
      return remindersRainDaysDao.getAllAlarmDayReminder()
   }

   override suspend fun deleteAllRemindersRainDays() {
      remindersRainDaysDao.deleteAllRemindersRainDays()
   }

   override suspend fun deleteOneReminderFromDatabase(id: Int) {
      remindersRainDaysDao.deleteOneReminderFromDatabase(id)
   }
}