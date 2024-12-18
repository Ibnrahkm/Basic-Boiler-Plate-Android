package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutLoginBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.HomeActivity


class LoginFragment : BaseFragment() {

    private val RC_SIGN_IN: Int = 2
    lateinit var binding: LayoutLoginBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutLoginBinding>(
            inflater, R.layout.layout_login, null, false
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
            requireActivity().finish()
        }

        binding.btnSigninGoogle.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
            if (account == null) {
                val signInIntent = AppController.get(requireActivity()).mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            } else {
                model.loginSocial(account.email.toString(), account.displayName.toString()).observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            val user = gson.fromJson(it.data.toString(), User::class.java)
                            if (user.accessToken.isNullOrEmpty()) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Failed to login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                AppController.loggedIn(true)
                                AppController.setUserData(gson.toJson(user))
                                requireActivity().finishAffinity()
                                val intent = Intent(requireActivity(), HomeActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                startActivity(intent)
                            }
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(requireActivity(), "Failed to login", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }
        }
        binding.btnEyeslash.tag = false
        binding.btnEyeslash.setOnClickListener {
            if (!(binding.btnEyeslash.tag as Boolean)) {

                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.btnEyeslash.tag = true
                binding.btnEyeslash.setImageResource(R.drawable.eye)
            } else {
                binding.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance();
                binding.btnEyeslash.tag = false
                binding.btnEyeslash.setImageResource(R.drawable.eyeslash)
            }

        }
        binding.btnSubmit.setOnClickListener {
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                model.login(email, password).observe(viewLifecycleOwner, Observer {

                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            val user = gson.fromJson(it.data.toString(), User::class.java)
                            if (user.accessToken.isNullOrEmpty()) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Failed to login",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                AppController.loggedIn(true)
                                AppController.setUserData(gson.toJson(user))
                                requireActivity().finishAffinity()
                                val intent = Intent(requireActivity(), HomeActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                startActivity(intent)
                            }
                        } else {
                            Helper.hideProgressDialog()
                            Toast.makeText(requireActivity(), "Failed to login", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            }


            /*  Navigation.findNavController(binding.root)
                  .navigate(R.id.action_loginFragment_to_emailVerificationFragment)*/
        }
        binding.btnSignup.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnForgotPassword.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_forgotPassword)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(
                    ApiException::class.java
                )

                if (account!=null){
                    model.loginSocial(account.email.toString(), account.displayName.toString()).observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            if (it.status == Resource.Status.LOADING) {
                                Helper.showProgressDialog("", "", requireActivity())
                            } else if (it.status == Resource.Status.SUCCESS) {
                                Helper.hideProgressDialog()
                                val user = gson.fromJson(it.data.toString(), User::class.java)
                                if (user.accessToken.isNullOrEmpty()) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Failed to login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    AppController.loggedIn(true)
                                    AppController.setUserData(gson.toJson(user))
                                    requireActivity().finishAffinity()
                                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                    startActivity(intent)
                                }
                            } else {
                                Helper.hideProgressDialog()
                                Toast.makeText(requireActivity(), "Failed to login", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                }else{
                    Toast.makeText(requireActivity(), "Failed to login", Toast.LENGTH_SHORT)
                        .show()
                }

            } catch (e: ApiException) {
                e.printStackTrace()
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