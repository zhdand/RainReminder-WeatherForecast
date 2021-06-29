package com.example.rainreminderweatherforecast.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory
@Inject constructor(
   private val viewModelsMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {

      val viewModelProvider = viewModelsMap[modelClass]
         ?: throw IllegalArgumentException("ViewModel $modelClass not found")

      try {
         return viewModelProvider.get() as T
      }catch (e: Exception){
         throw RuntimeException(e)
      }
   }
}