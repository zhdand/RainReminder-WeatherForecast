package com.example.rainreminderweatherforecast.di.app

import com.example.rainreminderweatherforecast.utils.CoroutinesDispatchersWrapperImpl
import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoroutinesDispatchersModule {

   @Provides
   @Singleton
   fun provideDispatcher(): ICoroutinesDispatchersWrapper = CoroutinesDispatchersWrapperImpl()
}