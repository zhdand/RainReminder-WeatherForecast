package com.example.rainreminderweatherforecast.ui.reminders_rain_days

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rainreminderweatherforecast.domain.usecases.DeleteAllRemindersRainDaysUseCase
import com.example.rainreminderweatherforecast.domain.usecases.DeleteOneReminderFromDatabaseUseCase
import com.example.rainreminderweatherforecast.domain.usecases.GetAllRemindersRainDaysUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ReminderRainDayViewModelFactory
@Inject constructor(
   private val mApplication: Application,
   private val getAllRemindersRainDaysUseCase: GetAllRemindersRainDaysUseCase,
   private val deleteAllReminderRainDayDaysUseCase: DeleteAllRemindersRainDaysUseCase,
   private val deleteOneReminderFromDatabaseUseCase: DeleteOneReminderFromDatabaseUseCase,
   private val dispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {
   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(ReminderRainDayViewModel::class.java)) {
         return ReminderRainDayViewModel(
            mApplication,
            getAllRemindersRainDaysUseCase,
            deleteAllReminderRainDayDaysUseCase,
            deleteOneReminderFromDatabaseUseCase,
            dispatcher
         ) as T
      }
      throw  IllegalArgumentException("Unknown ViewModel call ReminderRainDayViewModel")
   }
}