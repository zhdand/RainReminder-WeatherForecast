package com.example.rainreminderweatherforecast.ui.current_weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.example.rainreminderweatherforecast.domain.models.CurrentWeather
import com.example.rainreminderweatherforecast.domain.usecases.*
import com.example.rainreminderweatherforecast.getOrAwaitValueJUnit
import com.example.rainreminderweatherforecast.repository.FakeRepositoryCurrentWeather
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrentWeatherViewModelTest {

   @get:Rule
   val rule = InstantTaskExecutorRule()

   @ExperimentalCoroutinesApi
   @get:Rule
   val testCoroutineRule = MyCoroutineTestRule()

   private lateinit var viewModel: CurrentWeatherViewModel

   @Spy
   private var getCurrentWeatherFromNetworkUseCase: GetCurrentWeatherFromNetworkUseCase =
      GetCurrentWeatherFromNetworkUseCase(FakeRepositoryCurrentWeather())

   @Spy
   private var getNetworkResponseCurrentWeatherUseCase: GetNetworkResponseCurrentWeatherUseCase =
      GetNetworkResponseCurrentWeatherUseCase(FakeRepositoryCurrentWeather())

   @Mock
   private lateinit var getCurrentWeatherFromDatabaseUseCase: GetCurrentWeatherFromDatabaseUseCase

   @Mock
   private lateinit var isNetworkAvailableUseCase: IsNetworkAvailableUseCase

   private lateinit var fakePreferencesUseCase: FakeGetPreferencesUseCase

   @Mock
   private lateinit var isCityOrCountryEmptyInSettingsUseCase: IsCityOrCountryEmptyInSettingsUseCase

   @ExperimentalCoroutinesApi
   @Before
   fun setUp() {
      fakePreferencesUseCase = FakeGetPreferencesUseCase()
   }

   @After
   fun tearDown() {

   }

   @ExperimentalCoroutinesApi
   @Test
   fun getCurrentWeather_returnSuccess() = testCoroutineRule.testDispatcher.runBlockingTest {
      // GIVEN
      val currentWeather = CurrentWeather(
         1, "03.06.2021", "Dnepr", 15.0,
         5.0, 3.0, "image", "now", "Sunny"
      )
      val currentWeatherLiveData = liveData { emit(currentWeather) }
      `when`(getCurrentWeatherFromDatabaseUseCase.invoke()).thenReturn(currentWeatherLiveData)

      viewModel = CurrentWeatherViewModel(
         getCurrentWeatherFromNetworkUseCase,
         getCurrentWeatherFromDatabaseUseCase,
         getNetworkResponseCurrentWeatherUseCase,
         isNetworkAvailableUseCase,
         fakePreferencesUseCase,
         isCityOrCountryEmptyInSettingsUseCase,
         testCoroutineRule.testDispatchersWrapper
      )

      // WHEN
      viewModel.getCurrentWeather()

      // THAN
      assertThat(viewModel.networkResponseResult.getOrAwaitValueJUnit()).isEqualTo(
         ResultWrapper.Success
      )
      assertThat(viewModel.currentWeatherFromDatabase.getOrAwaitValueJUnit()).isEqualTo(
         currentWeather
      )
      verify(getCurrentWeatherFromDatabaseUseCase).invoke()
      verify(getCurrentWeatherFromNetworkUseCase, times(1)).invoke()
   }

   @Test
   fun `is city name in settings is empty, return true`() {
      `when`(isCityOrCountryEmptyInSettingsUseCase()).then { true }
      isCityOrCountryEmptyInSettingsUseCase()

      //than
      verify(isCityOrCountryEmptyInSettingsUseCase, times(1)).invoke()
      assertThat(isCityOrCountryEmptyInSettingsUseCase()).isEqualTo(true)
   }

}
