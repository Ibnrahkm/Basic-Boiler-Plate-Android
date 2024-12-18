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
import com.ibt.ava.databinding.LayoutEmailVerificationBinding
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.HomeActivity
import org.json.JSONObject


class EmailVerificationFragment : BaseFragment() {

    lateinit var binding: LayoutEmailVerificationBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutEmailVerificationBinding>(
            inflater, R.layout.layout_email_verification, null, false
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
        var m = requireArguments().getString("email").toString() + "ئیمەیڵێکتان بۆ دێت بەم ئیمەیڵە "
        var from = requireArguments().getString("from")
        binding.tvMessage.text = m
        binding.btnSubmit.setOnClickListener {
            var code = binding.etOtp.text.toString()
            if (!code.isNullOrEmpty()) {
                if (from.equals("forgot_password")) {
                    model.verifyForgotPasswordOtp(
                        requireArguments().getString("userId").toString(),
                        code
                    )
                        .observe(viewLifecycleOwner, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = JSONObject(it.data.toString())
                                    if (data.has("success") && data.getBoolean("success")) {
                                        var b = Bundle().apply {
                                            putString(
                                                "userId",
                                                requireArguments().getString("userId").toString()
                                            )
                                        }
                                        Navigation.findNavController(
                                            requireActivity(),
                                            R.id.nav_host_fragment_activity_home
                                        )
                                            .navigate(
                                                R.id.action_emailVerificationFragment_to_resetPasswordFragment,
                                                b
                                            )
                                    }
                                } else {
                                    Helper.hideProgressDialog()
                                    Toast.makeText(
                                        requireActivity(),
                                        "Failed to login",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        })
                } else {
                    model.verifyOtp(code, requireArguments().getString("userId").toString())
                        .observe(viewLifecycleOwner, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    if (from.equals("forgot_password")) {


                                        Navigation.findNavController(
                                            requireActivity(),
                                            R.id.nav_host_fragment_activity_home
                                        )
                                            .navigate(R.id.action_emailVerificationFragment_to_resetPasswordFragment)

                                    } else {
                                        val user =
                                            gson.fromJson(it.data.toString(), User::class.java)
                                        if (user.accessToken.isNullOrEmpty()) {
                                            Toast.makeText(
                                                requireActivity(),
                                                "Failed to verify otp",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            AppController.loggedIn(true)
                                            AppController.setUserData(gson.toJson(user))
                                            requireActivity().finishAffinity()
                                            val intent =
                                                Intent(requireActivity(), HomeActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                            startActivity(intent)
                                        }
                                    }

                                } else {
                                    Helper.hideProgressDialog()
                                    Toast.makeText(
                                        requireActivity(),
                                        "Failed to login",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        })
                }
            }
        }
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.btnResend.setOnClickListener {
            model.regenerateOtp(requireArguments().getString("userId").toString())
                .observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data = JSONObject(it.data.toString())
                            if (data.has("success") && data.getBoolean("success")) {
                                Toast.makeText(
                                    requireActivity(),
                                    "otp resend successful",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(
                                requireActivity(),
                                "Failed to login",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                })
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