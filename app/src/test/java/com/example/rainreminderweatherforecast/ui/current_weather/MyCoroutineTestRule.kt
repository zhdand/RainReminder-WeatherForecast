package com.example.rainreminderweatherforecast.ui.current_weather

import com.example.rainreminderweatherforecast.utils.ICoroutinesDispatchersWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MyCoroutineTestRule constructor(
   val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

   val testDispatchersWrapper: ICoroutinesDispatchersWrapper = object: ICoroutinesDispatchersWrapper{
      override val main: CoroutineDispatcher
         get() = testDispatcher
      override val default: CoroutineDispatcher
         get() = testDispatcher
      override val io: CoroutineDispatcher
         get() = testDispatcher
   }

   override fun starting(description: Description?) {
      super.starting(description)
      Dispatchers.setMain(testDispatcher)
   }

   override fun finished(description: Description?) {
      super.finished(description)
      Dispatchers.resetMain()
      testDispatcher.cleanupTestCoroutines()
   }
}