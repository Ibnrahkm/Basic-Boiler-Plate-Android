package com.ibt.ava.view.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ibt.ava.AppController
import com.ibt.ava.dagger.component.DaggerBaseActivityComponent
import com.ibt.ava.dagger.component.DaggerBaseFragmentComponent
import com.ibt.ava.dagger.component.DaggerSplashComponent
import com.ibt.ava.dagger.module.ActivityModule
import com.ibt.ava.viewmodel.MainViewModel
import javax.inject.Inject


open class BaseActivity : AppCompatActivity() {


    companion object {

    }
    @Inject
    lateinit var model: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerBaseActivityComponent.builder()
            .appComponent(AppController.get(this).getApplicationComponent()).build().inject(this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}