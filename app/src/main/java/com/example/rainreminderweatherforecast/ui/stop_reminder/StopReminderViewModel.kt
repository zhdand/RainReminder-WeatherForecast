package com.example.rainreminderweatherforecast.ui.stop_reminder

import androidx.lifecycle.ViewModel
import com.example.rainreminderweatherforecast.domain.usecases.GetPreferencesUseCase
import javax.inject.Inject

class StopReminderViewModel
   @Inject constructor(
      preferencesUseCase: GetPreferencesUseCase
   ) : ViewModel() {

      val preferences = preferencesUseCase()
}