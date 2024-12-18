package com.ibt.ava.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.dagger.component.DaggerSplashComponent
import com.ibt.ava.service.model.news.details.NewsDetails
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.viewmodel.SplashViewModel

import javax.inject.Inject

/*
* splash screen
* */
class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_splash)
        if (!AppController.getFirstTime()) {
            AppController.setFirstTime(true)
            AppController.setNotificationEnabled(true)
        }
        if (intent.hasExtra("id")) {
            var id = intent.getStringExtra("id").toString()
            model.getNewsDetails(id).observe(this, Observer {
                if (it != null) {
                    if (it.status == Resource.Status.LOADING) {
                        Helper.showProgressDialog("", "", this)
                    } else if (it.status == Resource.Status.SUCCESS) {
                        Helper.hideProgressDialog()
                        var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                        finish()
                        if (data != null) {
                            if (!data.mediaGallery.isNullOrEmpty()) {
                                var i = Intent(this, SingleNewsSpecialDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                i.putExtra("video", false)
                                this.startActivity(i)
                            } else if (!data.youtubeKey.isNullOrEmpty()) {
                                var i = Intent(this, SingleNewsSpecialDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                i.putExtra("video", true)
                                this.startActivity(i)
                            } else if (data.primaryCategory.id == 12) {
                                var i = Intent(this, SingleNewsSpecialDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                i.putExtra("opinion", true)
                                this.startActivity(i)
                            } else if (data.isSpecialToAva) {
                                var i = Intent(this, SingleNewsDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                this.startActivity(i)
                            } else {
                                var i = Intent(this, SingleNewsDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                this.startActivity(i)
                            }
                        }
                    } else if (it.status == Resource.Status.ERROR) {
                        Helper.hideProgressDialog()
                    }
                }
            })
        } else {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                finish()
                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )
            }, 2000)
        }

    }

}
