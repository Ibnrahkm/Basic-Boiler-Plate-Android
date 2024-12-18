package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.ibt.ava.databinding.LayoutForgotPasswordEmailBinding
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.databinding.LayoutManageAccountBinding
import com.ibt.ava.databinding.LayoutProfileBinding
import com.ibt.ava.databinding.LayoutUpdateEmailBinding
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.HomeActivity
import org.json.JSONObject


class ForgotPassword : BaseFragment() {

    lateinit var binding: LayoutForgotPasswordEmailBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutForgotPasswordEmailBinding>(
            inflater, R.layout.layout_forgot_password_email, null, false
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
            var email = binding.etEmail.text.toString().trim()
            if (!email.isNullOrEmpty()) {
                model.forgotPassword(
                    binding.etEmail.text.toString().trim()
                )
                    .observe(viewLifecycleOwner, Observer {

                        if (it != null) {
                            if (it.status == Resource.Status.LOADING) {
                                Helper.showProgressDialog("", "", requireActivity())
                            } else if (it.status == Resource.Status.SUCCESS) {
                                Helper.hideProgressDialog()
                                var data = JSONObject(it.data.toString())
                                if (data.has("user_id")) {
                                    var b = Bundle().apply {
                                        putString("from", "forgot_password")
                                        putString("userId", data.getInt("user_id").toString())
                                        putString("email", binding.etEmail.text.toString().trim())
                                    }
                                    Navigation.findNavController(
                                        requireActivity(),
                                        R.id.nav_host_fragment_activity_home
                                    )
                                        .navigate(
                                            R.id.action_forgotPassword_to_emailVerificationFragment,
                                            b
                                        )
                                } else {
                                    binding.etEmail.setText("")
                                    Toast.makeText(
                                        requireActivity(),
                                        "Something went wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else if (it.status == Resource.Status.ERROR) {
                                Helper.hideProgressDialog()
                                Toast.makeText(
                                    requireActivity(), "Something went wrong", Toast.LENGTH_SHORT
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