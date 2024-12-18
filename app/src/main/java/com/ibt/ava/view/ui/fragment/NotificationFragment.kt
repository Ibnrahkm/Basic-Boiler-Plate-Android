package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFollowBinding
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.databinding.LayoutManageAccountBinding
import com.ibt.ava.databinding.LayoutNotificationsBinding
import com.ibt.ava.databinding.LayoutProfileBinding
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.notifications.Notification
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.NotificationAdapter
import org.json.JSONObject
import java.lang.reflect.Type


class NotificationFragment : BaseFragment() {

    lateinit var binding: LayoutNotificationsBinding
    var gson = Gson()
    lateinit var notificationAdapter: NotificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutNotificationsBinding>(
            inflater, R.layout.layout_notifications, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationAdapter = NotificationAdapter(requireActivity())
        binding.rec.adapter = notificationAdapter
notificationAdapter.model=model
        model.getAllNotifications().observe(this, Observer {
            if (it != null) {
                if (it.status == Resource.Status.LOADING) {
                    Helper.showProgressDialog("", "", requireActivity())
                } else if (it.status == Resource.Status.SUCCESS) {
                    Helper.hideProgressDialog()
                 var n=gson.fromJson(it.data.toString(),Notification::class.java)
                    notificationAdapter.provideData(n.data)
                } else {
                    Helper.hideProgressDialog()
                }
            }
        })
    }
}