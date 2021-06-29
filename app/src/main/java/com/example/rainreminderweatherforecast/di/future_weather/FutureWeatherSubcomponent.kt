package com.example.rainreminderweatherforecast.di.future_weather

import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.ui.future_days_list.FutureWeatherListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FutureWeatherViewModelsModule::class])
interface FutureWeatherSubcomponent {

   @Subcomponent.Factory
   interface Factory {
      fun create(): FutureWeatherSubcomponent
   }

   fun inject(futureWeatherFragment: FutureWeatherListFragment)

}