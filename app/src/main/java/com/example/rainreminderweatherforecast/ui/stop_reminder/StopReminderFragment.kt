package com.example.rainreminderweatherforecast.ui.stop_reminder

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.rainreminderweatherforecast.MyApp
import com.example.rainreminderweatherforecast.R
import com.example.rainreminderweatherforecast.service.AlarmService
import com.google.android.material.button.MaterialButton
import javax.inject.Inject

/**
 * A fragment to stop a reminder.
 */
class StopReminderFragment : Fragment(R.layout.stop_reminder_fragment) {

  companion object {
    fun newInstance() = StopReminderFragment()
  }

  @Inject
  lateinit var viewModelProviderFactory: ViewModelProvider.Factory
  lateinit var viewModel: StopReminderViewModel

  private lateinit var textViewMessage: TextView
  private lateinit var buttonOk: MaterialButton
  private lateinit var imageView: ImageView

  override fun onAttach(context: Context) {
    super.onAttach(context)

    activity?.run {
      (application as MyApp).getMyAppComponent().inject(this@StopReminderFragment)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel = ViewModelProvider(this, viewModelProviderFactory)[StopReminderViewModel::class.java]

    textViewMessage = view.findViewById(R.id.text_view_stop_reminder_message)
    buttonOk = view.findViewById(R.id.button_stop_reminder)
    imageView = view.findViewById(R.id.imageview_stop_reminder_umbrella)

    textViewMessage.text = viewModel.preferences.getRemindersMessage()

    buttonOk.setOnClickListener {
      Intent(requireContext(), AlarmService::class.java).also { intent ->
        activity?.stopService(intent)
      }
      activity?.finish()
    }

  }

}