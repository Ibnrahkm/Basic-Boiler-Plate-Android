package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFollowBinding
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.databinding.LayoutManageAccountBinding
import com.ibt.ava.databinding.LayoutProfileBinding
import com.ibt.ava.databinding.LayoutUpdatePasswordBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import org.json.JSONObject


class ManageUpdatePasswordFragment : BaseFragment() {

    lateinit var binding: LayoutUpdatePasswordBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutUpdatePasswordBinding>(
            inflater, R.layout.layout_update_password, null, false
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
        val user = Gson().fromJson(AppController.getUserData(), User::class.java)

        binding.btnSubmit.setOnClickListener {

            var password = binding.etNewPassword.text.toString()
            var confirmPassword = binding.etOldPassword.text.toString()
            if (!password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()) {
                model.resetPassword(
                    user.user__1.id.toString(), password, confirmPassword
                ).observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data = JSONObject(it.data.toString())
                            if (data.has("success") && data.getBoolean("success")) {
                                Toast.makeText(
                                    requireActivity(),
                                    "password update successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "Failed to update password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(
                                requireActivity(), "Failed to update password", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handleAppbar(true)
    }
}