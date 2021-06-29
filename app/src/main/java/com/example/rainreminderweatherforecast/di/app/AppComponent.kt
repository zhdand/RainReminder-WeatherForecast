package com.example.rainreminderweatherforecast.di.app

import android.app.Application
import android.content.Context
import com.example.rainreminderweatherforecast.di.app.*
import com.example.rainreminderweatherforecast.di.current_weather.CurrentWeatherSubcomponent
import com.example.rainreminderweatherforecast.di.factory.ViewModelFactory
import com.example.rainreminderweatherforecast.di.factory.ViewModelFactoryModule
import com.example.rainreminderweatherforecast.di.future_weather.FutureWeatherSubcomponent
import com.example.rainreminderweatherforecast.di.reminder_rain_day.ReminderRainDaysSubcomponent
import com.example.rainreminderweatherforecast.service.AlarmService
import com.example.rainreminderweatherforecast.ui.stop_reminder.StopReminderFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
   modules = [
      AppSubcomponentsModule::class,
      ViewModelFactoryModule::class,
      NetworkModule::class,
      DatabaseModule::class,
      RepositoryWeatherModule::class,
      IsNetworkAvailableProviderModule::class,
      PreferencesProviderModule::class,
      LocationProviderModule::class,
      CoroutineDispatcherModule::class
   ]
)
interface AppComponent {

   fun currentWeatherSubcomponent(): CurrentWeatherSubcomponent.Factory
   fun futureWeatherSubcomponent(): FutureWeatherSubcomponent.Factory
   fun reminderRainDaysSubcomponent(): ReminderRainDaysSubcomponent.Factory

   fun getViewModel(): ViewModelFactory

   fun inject(stopReminderFragment: StopReminderFragment)
   fun inject(alarmService: AlarmService)

   @Component.Builder
   interface Builder {
      @BindsInstance
      fun context(context: Context): Builder

      @BindsInstance
      fun app(app: Application): Builder

      fun build(): AppComponent
   }

}