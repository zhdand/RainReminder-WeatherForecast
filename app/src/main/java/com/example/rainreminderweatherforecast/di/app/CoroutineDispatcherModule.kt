package com.example.rainreminderweatherforecast.di.app

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CoroutineDispatcherModule {

   @Provides
   @Singleton
   fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}