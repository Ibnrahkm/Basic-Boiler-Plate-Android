package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
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
import com.ibt.ava.databinding.LayoutManageAccountBinding
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource


class ManageAccountFragment : BaseFragment() {

    lateinit var binding: LayoutManageAccountBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutManageAccountBinding>(
            inflater, R.layout.layout_manage_account, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleAppbar(false)

        var user = gson.fromJson(AppController.getUserData(), User::class.java)
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        binding.btnSubmit.setOnClickListener {
            var name = binding.etFullName.text.toString().trim()

            if (!name.isNullOrEmpty()) {
                model.update("Bearer ${user.accessToken}", user.user__1.id.toString(), name, "")
                    .observe(viewLifecycleOwner, Observer {

                        if (it != null) {
                            if (it.status == Resource.Status.LOADING) {
                                Helper.showProgressDialog("", "", requireActivity())
                            } else if (it.status == Resource.Status.SUCCESS) {
                                Helper.hideProgressDialog()
                                user.user__1.name = name
                                AppController.setUserData(gson.toJson(user))
                                Toast.makeText(requireActivity(), "Updated!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Helper.hideProgressDialog()
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