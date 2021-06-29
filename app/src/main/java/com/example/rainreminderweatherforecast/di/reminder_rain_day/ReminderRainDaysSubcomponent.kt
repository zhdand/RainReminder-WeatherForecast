package com.example.rainreminderweatherforecast.di.reminder_rain_day

import com.example.myweatherapp.di.reminder_rain_day.ReminderRainDaysViewModelsModule
import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.ui.reminders_rain_days.ReminderRainDayFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ReminderRainDaysViewModelsModule::class])
interface ReminderRainDaysSubcomponent {

   @Subcomponent.Factory
   interface Factory {
      fun create(): ReminderRainDaysSubcomponent
   }

   fun inject(reminderRainDayFragment: ReminderRainDayFragment)

}