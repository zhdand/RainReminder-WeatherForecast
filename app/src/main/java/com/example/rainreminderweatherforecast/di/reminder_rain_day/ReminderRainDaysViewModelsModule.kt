package com.example.myweatherapp.di.reminder_rain_day

import androidx.lifecycle.ViewModel
import com.example.rainreminderweatherforecast.di.annotations.ViewModelKey
import com.example.rainreminderweatherforecast.ui.reminders_rain_days.ReminderRainDayViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ReminderRainDaysViewModelsModule {

   @Binds
   @IntoMap
   @ViewModelKey(ReminderRainDayViewModel::class)
   internal abstract fun bindReminderRainDayViewModel(viewModel: ReminderRainDayViewModel): ViewModel

}