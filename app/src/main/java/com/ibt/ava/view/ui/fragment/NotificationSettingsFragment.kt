package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFollowBinding
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.databinding.LayoutManageAccountBinding
import com.ibt.ava.databinding.LayoutNotificationSettingsBinding
import com.ibt.ava.databinding.LayoutNotificationsBinding
import com.ibt.ava.databinding.LayoutProfileBinding
import com.ibt.ava.service.model.notifications.Notification
import com.ibt.ava.util.Helper
import com.ibt.ava.view.adapter.NotificationAdapter


class NotificationSettingsFragment : BaseFragment() {

    lateinit var binding: LayoutNotificationSettingsBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutNotificationSettingsBinding>(
            inflater, R.layout.layout_notification_settings, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleAppbar(false)
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.notif.isChecked = AppController.getNotificationEnabled()
        binding.notif.setOnCheckedChangeListener { switchView, isChecked ->
            AppController.setNotificationEnabled(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handleAppbar(true)
    }
}