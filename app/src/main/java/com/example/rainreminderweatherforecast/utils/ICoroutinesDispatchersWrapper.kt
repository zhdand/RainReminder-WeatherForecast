package com.example.rainreminderweatherforecast.utils

import kotlinx.coroutines.CoroutineDispatcher

interface ICoroutinesDispatchersWrapper {
  val main: CoroutineDispatcher
  val default: CoroutineDispatcher
  val io: CoroutineDispatcher
}