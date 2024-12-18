package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
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
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.databinding.LayoutSignupBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.DoNothingTransformationMethod
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.HomeActivity
import org.json.JSONObject


class SignupFragment : BaseFragment() {

    lateinit var binding: LayoutSignupBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutSignupBinding>(
            inflater, R.layout.layout_signup, null, false
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

        binding.btnSigninGoogle.setOnClickListener {

        }
        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_signupFragment_to_loginFragment)
        }
        binding.btnEyeslash.tag = false
        binding.btnEyeslash.setOnClickListener {
            if (!(binding.btnEyeslash.tag as Boolean)) {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnEyeslash.tag = true
                binding.btnEyeslash.setImageResource(R.drawable.eye)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnEyeslash.tag = false
                binding.btnEyeslash.setImageResource(R.drawable.eyeslash)
            }

        }

        binding.btnConfirmEyeslash.tag = false
        binding.btnConfirmEyeslash.setOnClickListener {
            if (!(binding.btnConfirmEyeslash.tag as Boolean)) {
                binding.etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.btnConfirmEyeslash.tag = true
                binding.btnConfirmEyeslash.setImageResource(R.drawable.eye)
            } else {
                binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.btnConfirmEyeslash.tag = false
                binding.btnConfirmEyeslash.setImageResource(R.drawable.eyeslash)
            }

        }
        binding.btnSubmit.setOnClickListener {

            var email = binding.etEmail.text.toString().trim()
            var name = binding.etFullName.text.toString().trim()
            var password = binding.etPassword.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !name.isNullOrEmpty()) {
                model.signup(email, password, name).observe(viewLifecycleOwner, Observer {

                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var json = JSONObject(it.data.toString())
                            if (json.has("message")) {
                                Helper.showAlertDialog(
                                    200, "Info", json.getString("message"), requireActivity()
                                ).setOnDismissListener {
                                    if (json.has("user_id")) {
                                        val b = Bundle()
                                        b.putString("userId", json.getString("user_id"))
                                        b.putString("email", binding.etEmail.text.toString().trim())
                                        b.putString("from","signup")
                                        Navigation.findNavController(binding.root).navigate(
                                            R.id.action_signupFragment_to_emailVerificationFragment,
                                            b
                                        )
                                    }
                                }
                            }


                            /*  Log.e("ascvsdvsdvsd", it.data.toString())
                              AppController.loggedIn(true)
                              AppController.setUserData(gson.toJson(user))
                              requireActivity().finishAffinity()
                              val intent = Intent(requireActivity(), HomeActivity::class.java)
                              intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                              startActivity(intent)*/
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(
                                requireActivity(), "Failed to signup", Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                })
            }


            /* Navigation.findNavController(binding.root)
                .navigate(R.id.action_signupFragment_to_emailVerificationFragment)*/
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