package com.example.rainreminderweatherforecast.ui.future_days_list

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rainreminderweatherforecast.MyApp
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.databinding.FragmentFutureWeatherListDaysBinding
import com.example.rainreminderweatherforecast.di.factory.ViewModelFactory
import com.example.rainreminderweatherforecast.di.future_weather.FutureWeatherSubcomponent
import com.example.rainreminderweatherforecast.domain.models.FutureWeather
import com.example.rainreminderweatherforecast.domain.models.ReminderRainDay
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.utils.Constants
import javax.inject.Inject
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import com.example.rainreminderweatherforecast.utils.Converter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

/**
 * Weather Forecast with list RecyclerView.
 */
class FutureWeatherListFragment : Fragment(),
   FutureWeatherListAdapter.OnItemClickListenerRecyclerView {

   private lateinit var futureWeatherSubcomponent: FutureWeatherSubcomponent

   private var _binding: FragmentFutureWeatherListDaysBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var viewModelProviderFactory: ViewModelFactory
   private val viewModel: FutureWeatherListViewModel by viewModels { viewModelProviderFactory }

   override fun onAttach(context: Context) {
      injectDagger()
      super.onAttach(context)
   }

   private fun injectDagger() {
      val appComponent = activity?.run { (application as MyApp).getMyAppComponent() }
         ?: throw Exception("AppComponent is null")

      futureWeatherSubcomponent = appComponent.futureWeatherSubcomponent().create()
      futureWeatherSubcomponent.inject(this)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FragmentFutureWeatherListDaysBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initRecyclerViewAndCityName()

      initObservers()

      if (viewModel.preferences.isUsingCurrentDeviceLocation()) {
         checkLocationPermission()
      } else {
         observeNetworkStatus()
      }
   }

   private fun initObservers() {
      viewModel.networkResponseResult.observe(viewLifecycleOwner, { result ->
         when (result) {
            is ResultWrapper.Loading -> {
               updateUiProgressBar(View.VISIBLE)
            }
            is ResultWrapper.Error -> {
               updateUiProgressBar(View.GONE)
               Toast.makeText(
                  requireActivity(),
                  getString(result.errorMessage!!),
                  Toast.LENGTH_LONG
               ).show()
            }
            is ResultWrapper.Success -> {
               updateUiProgressBar(View.GONE)
            }
            is ResultWrapper.Empty -> {
               updateUiProgressBar(View.GONE)
            }
         }
      })

      viewModel.isCityCountryEmptyInSettings.observe(viewLifecycleOwner, { result ->
         when (result) {
            is ResultWrapper.Error -> {
               Toast.makeText(
                  requireContext(),
                  getString(result.errorMessage!!),
                  Toast.LENGTH_SHORT
               ).show()
            }
            else -> {}
         }
      })
   }

   private fun observeNetworkStatus() {
      viewModel.isNetworkAvailable.observe(viewLifecycleOwner, { isNetworkAvailable ->
         when (isNetworkAvailable) {
            ResultNetworkAvailableState.NetworkAvailable -> {
               updateUiIsNetworkConnected(true)
               viewModel.getFutureWeather()
            }
            ResultNetworkAvailableState.NetworkUnavailable -> {
               updateUiIsNetworkConnected(false)
            }
            ResultNetworkAvailableState.NetworkEmptyState -> {
               updateUiIsNetworkConnected(true)
            }
         }
      })
   }

   private fun updateUiIsNetworkConnected(isNetworkConnected: Boolean) {
      when (isNetworkConnected) {
         true -> {
            binding.textviewSecondIsNetworkConnected.visibility = View.GONE
         }
         false -> {
            binding.textviewSecondIsNetworkConnected.apply {
               visibility = View.VISIBLE
               setTextColor(ContextCompat.getColor(requireContext(), R.color.red_light))
               text = context.getString(R.string.message_internet_is_disconnected)
            }
         }
      }
   }

   private fun initRecyclerViewAndCityName() {
      val forecastWeatherAdapter = FutureWeatherListAdapter(this)
      viewModel.futureWeatherFromDatabase.observe(viewLifecycleOwner, { futureWeather ->
         forecastWeatherAdapter.data = futureWeather
         if (futureWeather.isNotEmpty()) {
            observeCityName(futureWeather[0].cityName)
         }
      })

      binding.rvListFutureWeather.apply {
         layoutManager = LinearLayoutManager(requireContext())
         addItemDecoration(
            DividerItemDecoration(
               requireContext(),
               DividerItemDecoration.VERTICAL
            )
         )
         adapter = forecastWeatherAdapter
      }
   }

   private fun updateUiProgressBar(visibility: Int) {
      binding.progressBarFuture.visibility = visibility
   }

   @SuppressLint("SetTextI18n")
   private fun observeCityName(cityName: String) {
      binding.textviewCityName.text = getString(R.string.future_fragment_city_name) + cityName
   }

   override fun onItemClick(position: Int) {
      val item = viewModel.futureWeatherFromDatabase.value?.get(position)
      item?.let { openTimePicker(item) }
   }

   private fun openTimePicker(futureWeather: FutureWeather) {
      val hour: Int
      val minute: Int
      if (viewModel.preferences.getRemindersTimeByDefault() == "") {
         hour = 0
         minute = 0
      } else {
         hour = viewModel.preferences.getRemindersTimeByDefault()?.take(2)?.toInt() ?: 7
         minute = viewModel.preferences.getRemindersTimeByDefault()?.takeLast(2)?.toInt() ?: 30
      }

      val picker = MaterialTimePicker.Builder()
         .setTimeFormat(TimeFormat.CLOCK_24H)
         .setTitleText(getString(R.string.reminder_title_time_picker_set_reminder_for_date) + " ${futureWeather.date}?")
         .setHour(hour)
         .setMinute(minute)
         .build()
      picker.show(childFragmentManager, "TAG Time Picker")
      picker.addOnPositiveButtonClickListener {
         val hourPicker = picker.hour
         val minutePicker = picker.minute
         val id = System.currentTimeMillis().toInt()
         val reminderRainDay = ReminderRainDay(
            id,
            futureWeather.date,
            futureWeather.cityName,
            hourPicker,
            minutePicker,
            futureWeather.description
         )

         val dateTimeReminderInMilliseconds =
            Converter.convertDateTimeToMilliseconds(reminderRainDay)

         if (dateTimeReminderInMilliseconds < System.currentTimeMillis()) {
            Toast.makeText(
               requireContext(),
               getString(R.string.message_time_picker_enter_time_grater_than_current),
               Toast.LENGTH_SHORT
            ).show()
         } else {
            setAlarmRainDay(reminderRainDay)
         }
      }
      picker.addOnNegativeButtonClickListener {

      }
      picker.addOnCancelListener {

      }
      picker.addOnDismissListener {

      }
   }

   private fun setAlarmRainDay(reminderRainDay: ReminderRainDay) {
      Toast.makeText(
         requireContext(),
         getString(R.string.message_reminder_is_set_on) + reminderRainDay.date,
         Toast.LENGTH_LONG
      )
         .show()
      viewModel.saveAlarm(reminderRainDay)
   }

   private fun checkLocationPermission() {
      if (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
         ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
         ) != PackageManager.PERMISSION_GRANTED
      ) {
         if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            MaterialAlertDialogBuilder(requireContext())
               .setTitle(getString(R.string.permission_rationale_title))
               .setMessage(
                  getString(R.string.permission_rationale_message)
               )
               .setPositiveButton(getString(R.string.permission_rationale_button_yes)) { dialog, _ ->
                  requestLocationPermission()
                  dialog.cancel()
               }
               .setNegativeButton(getString(R.string.permission_rationale_button_cancel)) { dialog, _ ->
                  dialog.cancel()
               }
               .setNeutralButton(getString(R.string.permission_rationale_button_custom)) { _, _ ->
                  viewModel.setPreferenceUseCurrentDeviceLocation(true)
                  viewModel.setPreferenceUseCustomLocation(false)
                  findNavController().navigate(R.id.action_FutureFragment_to_SettingsFragment)
               }
               .setCancelable(false)
               .show()

         } else {
            requestLocationPermission()
         }

      } else {
         observeNetworkStatus()
      }
   }

   private fun requestLocationPermission() {
      requestPermissions(
         arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
         ),
         Constants.PERMISSION_REQUEST_CODE_LOCATION
      )
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
      grantResults: IntArray
   ) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      when (requestCode) {
         Constants.PERMISSION_REQUEST_CODE_LOCATION -> {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(
                  requireContext(),
                  getString(R.string.message_location_permission_granted),
                  Toast.LENGTH_LONG
               ).show()
               viewModel.getFutureWeather()
            } else {
               Toast.makeText(
                  requireContext(),
                  getString(R.string.permission_rationale_toast_unable_location_permission),
                  Toast.LENGTH_LONG
               ).show()
            }
         }
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}