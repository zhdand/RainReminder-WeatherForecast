package com.example.rainreminderweatherforecast.ui.current_weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.example.rainreminderweatherforecast.MyApp
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.databinding.FragmentCurrentWeatherBinding
import com.example.rainreminderweatherforecast.di.current_weather.CurrentWeatherSubcomponent
import com.example.rainreminderweatherforecast.repository.network.IWeatherAPIService
import com.example.rainreminderweatherforecast.repository.providers.internet_connection.ResultNetworkAvailableState
import com.example.rainreminderweatherforecast.ui.models.CurrentWeatherUI
import com.example.rainreminderweatherforecast.utils.Constants.PERMISSION_REQUEST_CODE_LOCATION
import javax.inject.Inject
import com.example.rainreminderweatherforecast.utils.ResultWrapper
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Current weather fragment - default destination in the navigation.
 */
class CurrentWeatherFragment : Fragment() {

   private lateinit var currentWeatherSubcomponent: CurrentWeatherSubcomponent

   private var _binding: FragmentCurrentWeatherBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var myViewModelFactory: ViewModelProvider.Factory
   lateinit var viewModel: CurrentWeatherViewModel

   override fun onAttach(context: Context) {
      inject()
      super.onAttach(context)
   }

   private fun inject() {

      val appComponent = activity?.run { (application as MyApp).getMyAppComponent() }
         ?: throw Exception("AppComponent is null")

      currentWeatherSubcomponent = appComponent.currentWeatherSubcomponent().create()
      currentWeatherSubcomponent.inject(this)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initViewModel()

      initObservers()

      if (viewModel.preferences.isUsingCurrentDeviceLocation()) {
         checkLocationPermission()
      } else {
         observeNetworkStatus()
      }
   }

   private fun initViewModel() {
      viewModel =
         ViewModelProvider(this, myViewModelFactory)[CurrentWeatherViewModel::class.java]
   }

   private fun initObservers() {

      viewModel.currentWeatherFromDatabase.observe(viewLifecycleOwner, { currentWeather ->
         currentWeather?.let { weather ->
            updateUiWeather(weather.mapToUI())
         }
      })

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
//             Toast.makeText(requireActivity(), "OK !", Toast.LENGTH_LONG).show()
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
               viewModel.getCurrentWeather()
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

   private fun updateUiWeather(weather: CurrentWeatherUI) {
      binding.textviewCurrentWeatherCity.text = weather.cityName
      binding.textviewCurrentWeatherTemperature.text = weather.temperature
      binding.textviewCurrentWeatherWind.text = weather.wind
      binding.textviewCurrentWeatherPrecipitation.text = weather.precipitation
      binding.textviewCurrentWeatherDescription.text = weather.description

      Glide.with(this@CurrentWeatherFragment)
         .load("${IWeatherAPIService.URL_ICON}${weather.image}.png")
         .transition(GenericTransitionOptions.with(R.anim.icon_anim))
         .into(binding.imageIconCurrentWeather)
   }

   private fun updateUiIsNetworkConnected(isNetworkConnected: Boolean) {
      when (isNetworkConnected) {
         true -> {
            binding.textviewIsNetworkConnected.visibility = View.GONE
         }
         false -> {
            binding.textviewIsNetworkConnected.apply {
               visibility = View.VISIBLE
            }
         }
      }
   }

   private fun updateUiProgressBar(visibility: Int) {
      binding.progressBarCurrent.visibility = visibility
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
                  findNavController().navigate(R.id.action_CurrentFragment_to_SettingsFragment)
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
         PERMISSION_REQUEST_CODE_LOCATION
      )
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
      grantResults: IntArray
   ) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      when (requestCode) {
         PERMISSION_REQUEST_CODE_LOCATION -> {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(
                  requireContext(),
                  getString(R.string.message_location_permission_granted),
                  Toast.LENGTH_LONG
               ).show()
               viewModel.getCurrentWeather()
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

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}