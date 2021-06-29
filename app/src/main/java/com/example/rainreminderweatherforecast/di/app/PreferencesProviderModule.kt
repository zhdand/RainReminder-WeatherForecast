package com.example.rainreminderweatherforecast.di.app

import com.example.rainreminderweatherforecast.domain.IRepositoryPreferences
import com.example.rainreminderweatherforecast.domain.usecases.GetPreferencesUseCase
import com.example.rainreminderweatherforecast.domain.usecases.IGetPreferencesUseCase
import com.example.rainreminderweatherforecast.repository.RepositoryPreferencesImpl
import com.example.rainreminderweatherforecast.repository.providers.preferences.IPreferencesProvider
import com.example.rainreminderweatherforecast.repository.providers.preferences.PreferencesProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PreferencesProviderModule {

   @Singleton
   @Binds
   abstract fun providePreferences(preferencesProvider: PreferencesProviderImpl): IPreferencesProvider

   @Singleton
   @Binds
   abstract fun provideRepositoryPreferences(repositoryPreferences: RepositoryPreferencesImpl): IRepositoryPreferences

   @Singleton
   @Binds
   abstract fun provideGetPreferencesUseCase(getPreferencesUseCase: GetPreferencesUseCase): IGetPreferencesUseCase

}
