package com.example.rainreminderweatherforecast.ui.reminders_rain_days

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.rainreminderweatherforecast.broadcastreceiver.MyAlarmReceiver
import com.example.rainreminderweatherforecast.domain.usecases.DeleteAllRemindersRainDaysUseCase
import com.example.rainreminderweatherforecast.domain.usecases.DeleteOneReminderFromDatabaseUseCase
import com.example.rainreminderweatherforecast.domain.usecases.GetAllRemindersRainDaysUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReminderRainDayViewModel
@Inject constructor(
   private val mApplication: Application,
   getAllRemindersRainDaysUseCase: GetAllRemindersRainDaysUseCase,
   private val deleteAllReminderRainDayDaysUseCase: DeleteAllRemindersRainDaysUseCase,
   private val deleteOneReminderFromDatabaseUseCase: DeleteOneReminderFromDatabaseUseCase,
   private val dispatcher: CoroutineDispatcher
) : AndroidViewModel(mApplication) {

   val reminders = getAllRemindersRainDaysUseCase()

   /**
    * Deletes all reminders from the database and cancels all alarms.
    */
   fun clearAllRemindersRainDays() {
      viewModelScope.launch(dispatcher) {
         deleteAllReminderRainDayDaysUseCase()
      }
      cancelAllAlarms()
   }

   /**
    * Deletes one reminder with specific id from the database and cancel that alarm.
    * @param reminderId - is specific for each alarm.
    * */
   fun cancelOnReminderRainDay(reminderId: Int) {
      deleteOneReminderFromDatabase(reminderId)
      cancelAlarm(reminderId)
   }

   /**
    * Cancels all alarms.
    */
   private fun cancelAllAlarms() {
      reminders.value?.forEach {
         cancelAlarm(it.id)
      }
   }

   /**
    * Cancels an alarm with specific id
    * @param id - is specific for each alarm.
    */
   private fun cancelAlarm(id: Int) {
      val alarmManager = mApplication.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intentReceiver = Intent(mApplication, MyAlarmReceiver::class.java)
      val pendingIntent = PendingIntent.getBroadcast(mApplication, id, intentReceiver, 0)

      alarmManager.cancel(pendingIntent)
   }

   /**
    * Deletes on reminder with specific id from the database
    * @param id - is specific for each reminder.
    */
   private fun deleteOneReminderFromDatabase(id: Int) {
      viewModelScope.launch(dispatcher) {
         deleteOneReminderFromDatabaseUseCase(id)
      }
   }

}