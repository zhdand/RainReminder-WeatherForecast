package com.example.rainreminderweatherforecast.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rainreminderweatherforecast.di.annotations.ViewModelKey
import com.example.rainreminderweatherforecast.ui.stop_reminder.StopReminderViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

   @Binds
   internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

   @Binds
   @IntoMap
   @ViewModelKey(StopReminderViewModel::class)
   internal abstract fun bindStopReminderViewModel(viewModel: StopReminderViewModel): ViewModel

}