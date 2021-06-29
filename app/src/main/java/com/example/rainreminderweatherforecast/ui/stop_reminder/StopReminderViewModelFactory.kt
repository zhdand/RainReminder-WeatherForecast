package com.example.rainreminderweatherforecast.ui.stop_reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rainreminderweatherforecast.domain.usecases.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class StopReminderViewModelFactory
@Inject constructor(
   private val preferencesUseCase: GetPreferencesUseCase
) : ViewModelProvider.Factory {
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(StopReminderViewModelFactory::class.java))
         return StopReminderViewModelFactory(preferencesUseCase) as T
      throw IllegalArgumentException("Unknown ViewModel")
   }
}