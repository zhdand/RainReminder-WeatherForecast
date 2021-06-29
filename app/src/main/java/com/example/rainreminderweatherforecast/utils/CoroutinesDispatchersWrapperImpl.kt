package com.example.rainreminderweatherforecast.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutinesDispatchersWrapperImpl : ICoroutinesDispatchersWrapper {

  override val main: CoroutineDispatcher
    get() = Dispatchers.Main

  override val default: CoroutineDispatcher
    get() = Dispatchers.Default

  override val io: CoroutineDispatcher
    get() = Dispatchers.IO

}