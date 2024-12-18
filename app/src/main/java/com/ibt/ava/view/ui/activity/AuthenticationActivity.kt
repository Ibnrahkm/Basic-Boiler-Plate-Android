package com.ibt.ava.view.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.ibt.ava.R
import com.ibt.ava.databinding.ActivityAuthenticationBinding
import com.ibt.ava.databinding.ActivityHomeBinding
import com.ibt.ava.util.Helper
import com.ibt.ava.view.ui.fragment.CategoriesWiseNewsFragment
import com.ibt.ava.view.ui.fragment.LoginFragment
import com.ibt.ava.view.ui.fragment.ProfileFragment
import com.ibt.ava.view.ui.fragment.SearchFragment


class AuthenticationActivity : BaseActivity() {

    public lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}