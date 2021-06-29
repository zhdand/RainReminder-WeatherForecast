package com.example.rainreminderweatherforecast.domain

import androidx.lifecycle.LiveData
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay

interface IRepositoryReminders {

   suspend fun saveAlarm(reminderRainDay: ReminderRainDay)

   fun getAllAlarmDayReminder(): LiveData<List<ReminderRainDay>>

   suspend fun deleteAllRemindersRainDays()

   suspend fun deleteOneReminderFromDatabase(id: Int)
}