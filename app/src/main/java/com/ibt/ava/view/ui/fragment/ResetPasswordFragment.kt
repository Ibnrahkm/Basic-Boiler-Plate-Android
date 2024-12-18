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
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import com.ibt.ava.view.ui.activity.HomeActivity
import org.json.JSONObject


class ResetPasswordFragment : BaseFragment() {

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
        Helper.handleStatusBar(
            requireActivity().window!!, ContextCompat.getColor(
                requireActivity(), android.R.color.black
            ), requireActivity()
        )
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        binding.btnSubmit.setOnClickListener {

            var password = binding.etNewPassword.text.toString()
            var confirmPassword = binding.etOldPassword.text.toString()

            if (!password.isNullOrEmpty()&&!confirmPassword.isNullOrEmpty()) {
                model.resetPassword(
                    requireArguments().getString("userId").toString(), password, confirmPassword
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
                                    "password reset successful.Please login.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                    requireActivity().finish()
                                    val intent =
                                        Intent(
                                            requireActivity(),
                                            AuthenticationActivity::class.java
                                        )
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    startActivity(intent)
                                }, 200)

                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "Failed to reset password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(
                                requireActivity(), "Failed to reset password", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Helper.handleStatusBar(
            requireActivity().window!!, ContextCompat.getColor(
                requireActivity(), android.R.color.transparent
            ), requireActivity()
        )
    }
}