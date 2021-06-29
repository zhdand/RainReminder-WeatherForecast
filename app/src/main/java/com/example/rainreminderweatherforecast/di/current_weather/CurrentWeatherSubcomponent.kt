package com.example.rainreminderweatherforecast.di.current_weather

import com.example.rainreminderweatherforecast.di.annotations.FragmentScope
import com.example.rainreminderweatherforecast.ui.current_weather.CurrentWeatherFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CurrentWeatherViewModelsModule::class])
interface CurrentWeatherSubcomponent {

   @Subcomponent.Factory
   interface Factory {
      fun create(): CurrentWeatherSubcomponent
   }

   fun inject(currentWeatherFragment: CurrentWeatherFragment)

}