package com.example.rainreminderweatherforecast.di.current_weather

import androidx.lifecycle.ViewModel
import com.example.rainreminderweatherforecast.di.annotations.ViewModelKey
import com.example.rainreminderweatherforecast.ui.current_weather.CurrentWeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CurrentWeatherViewModelsModule {

   @Binds
   @IntoMap
   @ViewModelKey(CurrentWeatherViewModel::class)
   internal abstract fun bindCurrentWeatherViewModel(viewModel: CurrentWeatherViewModel): ViewModel

}