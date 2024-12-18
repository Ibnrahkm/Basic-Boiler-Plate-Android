package com.ibt.ava.view.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutProfileBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import com.ibt.ava.view.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_home.appbar
import kotlinx.android.synthetic.main.activity_home.fragment_container
import kotlinx.android.synthetic.main.activity_home.navView


class ProfileFragment : BaseFragment() {

    lateinit var binding: LayoutProfileBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutProfileBinding>(
            inflater, R.layout.layout_profile, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /* Helper.handleStatusBar(
             requireActivity().window!!, ContextCompat.getColor(
                 requireActivity(), android.R.color.black
             ), requireActivity()
         )*/
        (requireActivity() as HomeActivity).appbar.visibility = View.GONE
        var lp =
            (requireActivity() as HomeActivity).fragment_container.layoutParams as CoordinatorLayout.LayoutParams
        lp.behavior = null
        (requireActivity() as HomeActivity).fragment_container.requestLayout()
        (requireActivity() as HomeActivity).navView.visibility = View.GONE
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        binding.btnNotifications.setOnClickListener {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_profileFragment_to_notificationSettingsFragment)
        }

        binding.btnTalktous.setOnClickListener {

        }
        binding.btnPrivacyPolicy.setOnClickListener {

        }
        binding.btnTermsOfUse.setOnClickListener {

        }

        if (AppController.loggedIn()) {
            var user = gson.fromJson(AppController.getUserData(), User::class.java)
            model.profile("Bearer ${user.accessToken}").observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.status == Resource.Status.LOADING) {
                        Helper.showProgressDialog("", "", requireActivity())
                    } else if (it.status == Resource.Status.SUCCESS) {
                        Helper.hideProgressDialog()
                        var updated = gson.fromJson(it.data.toString(), User::class.java)
                        binding.tvName.text = updated.user__1.name
                        binding.tvEmail.text = updated.user__1.email
                        user.user__1 = updated.user__1
                        AppController.setUserData(gson.toJson(user))
                    } else {
                        Helper.hideProgressDialog()
                    }
                }
            })
            binding.btnLogout.setOnClickListener {

                var ob = MutableLiveData<Boolean>()
                Helper.showDecisionDialog(
                    "بەڵێ",
                    "نەخێر",
                    "دەرچوون لە دەرەوە",
                    "دڵنیای کە دەتەوێت بچیتە دەرەوە؟",
                    requireActivity(), ob
                )
                ob.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it) {
                            model.logout("Bearer ${user.accessToken}")
                                .observe(viewLifecycleOwner, Observer {

                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {
                                            Helper.showProgressDialog("", "", requireActivity())
                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            Helper.hideProgressDialog()
                                            try {
                                                AppController.get(requireActivity()).mGoogleSignInClient.signOut()
                                            } catch (ex: Exception) {

                                            }
                                            AppController.setUserData("")
                                            AppController.loggedIn(false)
                                            requireActivity().finishAffinity()
                                            val intent =
                                                Intent(requireActivity(), HomeActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                            startActivity(intent)
                                        } else {
                                            Helper.hideProgressDialog()
                                        }
                                    }
                                })
                        }
                        ob.value = null
                    }
                })
            }

            binding.btnDelete.setOnClickListener {
                var ob = MutableLiveData<Boolean>()
                Helper.showDecisionDialog(
                    "بەڵێ",
                    "نەخێر",
                    "ئەکاونتەکە بسڕەوە",
                    "دڵنیای دەتەوێت ئەکاونتەکەت بسڕیتەوە؟",
                    requireActivity(), ob
                )
                ob.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it) {
                            model.deleteAccount(
                                "Bearer ${user.accessToken}",
                                user.user__1.id.toString()
                            )
                                .observe(viewLifecycleOwner, Observer {

                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {
                                            Helper.showProgressDialog("", "", requireActivity())
                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            Helper.hideProgressDialog()
                                            AppController.setUserData("")
                                            AppController.loggedIn(false)
                                            requireActivity().finishAffinity()
                                            val intent =
                                                Intent(requireActivity(), HomeActivity::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                            startActivity(intent)
                                        } else {
                                            Helper.hideProgressDialog()
                                        }
                                    }
                                })
                        }
                        ob.value = null
                    }
                })
            }

            binding.btnManageAccount.setOnClickListener {

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_manageAccountFragment)
            }

            binding.btnSecurity.setOnClickListener {

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_manageUpdatePasswordFragment)
            }

        } else {
            binding.tvName.text = "چوونەژوورەوە"
            binding.layoutLogout.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
            binding.layoutSecurity.visibility = View.GONE
            binding.layoutManageAccount.visibility = View.GONE

            binding.tvName.setOnClickListener {
                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Helper.handleStatusBar(
            requireActivity().window!!, ContextCompat.getColor(
                requireActivity(), android.R.color.transparent
            ), requireActivity()
        )
        (requireActivity() as HomeActivity).appbar.visibility = View.VISIBLE
        var lp =
            (requireActivity() as HomeActivity).fragment_container.layoutParams as CoordinatorLayout.LayoutParams
        lp.behavior = AppBarLayout.ScrollingViewBehavior()
        (requireActivity() as HomeActivity).fragment_container.requestLayout()
        (requireActivity() as HomeActivity).navView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        try {
            var user = gson.fromJson(AppController.getUserData(), User::class.java)
            binding.tvName.text = user.user__1.name
            binding.tvEmail.text = user.user__1.email

        } catch (ex: Exception) {

        }
    }
}