package com.example.rainreminderweatherforecast.di.app

import com.example.rainreminderweatherforecast.di.current_weather.CurrentWeatherSubcomponent
import com.example.rainreminderweatherforecast.di.future_weather.FutureWeatherSubcomponent
import com.example.rainreminderweatherforecast.di.reminder_rain_day.ReminderRainDaysSubcomponent
import dagger.Module

@Module(
   subcomponents = [
      CurrentWeatherSubcomponent::class,
      FutureWeatherSubcomponent::class,
      ReminderRainDaysSubcomponent::class]
)
class AppSubcomponentsModule {
}