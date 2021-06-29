package com.example.rainreminderweatherforecast.di.future_weather

import androidx.lifecycle.ViewModel
import com.example.rainreminderweatherforecast.di.annotations.ViewModelKey
import com.example.rainreminderweatherforecast.ui.future_days_list.FutureWeatherListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FutureWeatherViewModelsModule {

   @Binds
   @IntoMap
   @ViewModelKey(FutureWeatherListViewModel::class)
   internal abstract fun bindFutureWeatherListViewModel(viewModel: FutureWeatherListViewModel): ViewModel

}