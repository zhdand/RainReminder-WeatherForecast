package com.example.rainreminderweatherforecast.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay

@Dao
interface IReminderRainDaysDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun updateOrInsertAlarm(reminderRainDay: ReminderRainDay)

   @Query("SELECT * FROM ${WeatherDatabase.TABLE_NAME_REMINDER_RAIN} ORDER BY 2")
   fun getAllAlarmDayReminder(): LiveData<List<ReminderRainDay>>

   @Query("DELETE FROM ${WeatherDatabase.TABLE_NAME_REMINDER_RAIN} ")
   suspend fun deleteAllRemindersRainDays()

   @Query("DELETE FROM ${WeatherDatabase.TABLE_NAME_REMINDER_RAIN}  WHERE id=:id")
   suspend fun deleteOneReminderFromDatabase(id: Int)

}