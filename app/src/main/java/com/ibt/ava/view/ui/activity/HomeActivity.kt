package com.ibt.ava.view.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.ActivityHomeBinding
import com.ibt.ava.util.Helper
import com.ibt.ava.view.ui.fragment.CategoriesWiseNewsFragment
import com.ibt.ava.view.ui.fragment.LoginFragment
import com.ibt.ava.view.ui.fragment.ProfileFragment
import com.ibt.ava.view.ui.fragment.SearchFragment


class HomeActivity : BaseActivity() {

    public lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Helper.handleStatusBar(window, true, this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        Helper.handleStatusBar(
            window!!, ContextCompat.getColor(
                this, android.R.color.transparent
            ), this
        )
        setContentView(binding.root)
        navButtonHandler("home")
        TedPermission.with(this).setPermissions(Manifest.permission.POST_NOTIFICATIONS)
            .setPermissionListener(
                object : PermissionListener {
                    override fun onPermissionGranted() {

                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    }
                }).check()
        binding.btnHome.setOnClickListener {
            navButtonHandler("home")
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.navigation_home)
        }
        binding.btnSearch.setOnClickListener {
            navButtonHandler("notification")
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.notificationFragment, null)
        }
        binding.btnVideo.setOnClickListener {
            navButtonHandler("video")
            binding.btnVideoTab.setImageResource(R.drawable.video)
            binding.btnImageTab.setImageResource(R.drawable.images_non)
            binding.btnMicTab.setImageResource(R.drawable.tab_mic_non)
            var arguments = Bundle()
            arguments.putBoolean("multimedia", true)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.multimediaFragment, arguments)
        }

        binding.btnFab.setOnClickListener {
            /*  if (AppController.loggedIn()) {
                  navButtonHandler("fab")
                  binding.btnBookmarkTab.setImageResource(R.drawable.bookmark)
                  binding.btnFollowTab.setImageResource(R.drawable.follow_non)
                  var arguments = Bundle()
                  arguments.putBoolean("bookmark", true)
                  Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                      .navigate(R.id.bookmarkFollowFragment, arguments)
              } else {
                  startActivity(Intent(this, AuthenticationActivity::class.java))
              }*/
            navButtonHandler("fab")
            binding.btnBookmarkTab.setImageResource(R.drawable.bookmark)
            binding.btnFollowTab.setImageResource(R.drawable.follow_non)
            var arguments = Bundle()
            arguments.putBoolean("bookmark", true)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.bookmarkFollowFragment, arguments)
        }
        binding.btnMore.setOnClickListener {
            navButtonHandler("more")
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.categoriesFragment)
        }

        binding.btnHam.setOnClickListener {
            var f = SearchFragment()
            var bun = Bundle()
            f.arguments = bun
            f.show((this).supportFragmentManager, null)
            // navButtonHandler("search")
            /* Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                 .navigate(R.id.navigation_vault)*/
        }

        binding.btnVideoTab.setOnClickListener {
            binding.btnVideoTab.setImageResource(R.drawable.video)
            binding.btnImageTab.setImageResource(R.drawable.images_non)
            binding.btnMicTab.setImageResource(R.drawable.tab_mic_non)

            var arguments = Bundle()
            arguments.putBoolean("multimedia", true)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.multimediaFragment, arguments)

        }
        binding.btnImageTab.setOnClickListener {
            Log.e("asdvsdvsdv", "sdvsdvsdvsd")
            binding.btnImageTab.setImageResource(R.drawable.images)
            binding.btnVideoTab.setImageResource(R.drawable.video_non)
            binding.btnMicTab.setImageResource(R.drawable.tab_mic_non)

            var arguments = Bundle()
            arguments.putBoolean("multimedia", false)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.multimediaFragment, arguments)
        }

        binding.btnMicTab.setOnClickListener {
            Log.e("asdvsdvsdv", "sdvsdvsdvsd")
            binding.btnImageTab.setImageResource(R.drawable.images_non)
            binding.btnVideoTab.setImageResource(R.drawable.video_non)
            binding.btnMicTab.setImageResource(R.drawable.tab_mic_selected)
            var arguments = Bundle()
            arguments.putBoolean("podcast", true)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.multimediaFragment, arguments)
        }


        binding.btnBookmarkTab.setOnClickListener {
            binding.btnBookmarkTab.setImageResource(R.drawable.bookmark)
            binding.btnFollowTab.setImageResource(R.drawable.follow_non)
            var arguments = Bundle()
            arguments.putBoolean("bookmark", true)
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                .navigate(R.id.bookmarkFollowFragment, arguments)

        }
        binding.btnFollowTab.setOnClickListener {
            if (AppController.loggedIn()) {
                Log.e("asdvsdvsdv", "sdvsdvsdvsd")
                binding.btnBookmarkTab.setImageResource(R.drawable.bookmark_non)
                binding.btnFollowTab.setImageResource(R.drawable.follow)
                var arguments = Bundle()
                arguments.putBoolean("bookmark", false)
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
                    .navigate(R.id.bookmarkFollowFragment, arguments)
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
        }



        binding.btnProfile.setOnClickListener {
            var options =
                NavOptions.Builder().setEnterAnim(R.anim.slide_up).setExitAnim(R.anim.slide_down)
                    .setPopEnterAnim(R.anim.slide_up).setPopExitAnim(R.anim.slide_down).build()
            Navigation.findNavController(
                this@HomeActivity,
                R.id.nav_host_fragment_activity_home
            ).navigate(R.id.profileFragment,null,options)
            /* if (AppController.loggedIn()) {
                 Navigation.findNavController(
                     this@HomeActivity,
                     R.id.nav_host_fragment_activity_home
                 ).navigate(R.id.profileFragment)
             } else {
                 startActivity(Intent(this, AuthenticationActivity::class.java))
             }*/
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    public fun navButtonHandler(clickedButton: String) {
        binding.appbar.setExpanded(true, true)
        when (clickedButton) {
            "home" -> {
                binding.layoutHam.visibility = View.VISIBLE
                binding.layoutSettings.visibility = View.GONE
                binding.layoutMul.visibility = View.GONE
                binding.layoutBookmark.visibility = View.GONE
                binding.layoutNotification.visibility = View.GONE
                binding.btnHome.setImageResource(R.drawable.tab_home_selected)
                binding.btnSearch.setImageResource(R.drawable.tab_notification_n_selected)
                binding.btnVideo.setImageResource(R.drawable.tab_video_n_selected)
                binding.btnFab.setImageResource(R.drawable.tab_fav_n_selected)
                binding.btnMore.setImageResource(R.drawable.tab_more_n_selected)
            }

            "notification" -> {
                binding.layoutHam.visibility = View.GONE
                binding.layoutSettings.visibility = View.GONE
                binding.layoutMul.visibility = View.GONE
                binding.layoutBookmark.visibility = View.GONE
                binding.layoutNotification.visibility = View.VISIBLE
                binding.btnHome.setImageResource(R.drawable.tab_home_n_selected)
                binding.btnSearch.setImageResource(R.drawable.tab_notification_selected)
                binding.btnVideo.setImageResource(R.drawable.tab_video_n_selected)
                binding.btnFab.setImageResource(R.drawable.tab_fav_n_selected)
                binding.btnMore.setImageResource(R.drawable.tab_more_n_selected)
            }

            "video" -> {

                binding.layoutHam.visibility = View.GONE
                binding.layoutSettings.visibility = View.GONE
                binding.layoutMul.visibility = View.VISIBLE
                binding.layoutBookmark.visibility = View.GONE
                binding.layoutNotification.visibility = View.GONE
                binding.btnHome.setImageResource(R.drawable.tab_home_n_selected)
                binding.btnSearch.setImageResource(R.drawable.tab_notification_n_selected)
                binding.btnVideo.setImageResource(R.drawable.tab_video_selected)
                binding.btnFab.setImageResource(R.drawable.tab_fav_n_selected)
                binding.btnMore.setImageResource(R.drawable.tab_more_n_selected)
            }

            "fab" -> {
                binding.layoutHam.visibility = View.GONE
                binding.layoutSettings.visibility = View.GONE
                binding.layoutMul.visibility = View.GONE
                binding.layoutBookmark.visibility = View.VISIBLE
                binding.layoutNotification.visibility = View.GONE
                binding.btnHome.setImageResource(R.drawable.tab_home_n_selected)
                binding.btnSearch.setImageResource(R.drawable.tab_notification_n_selected)
                binding.btnVideo.setImageResource(R.drawable.tab_video_n_selected)
                binding.btnFab.setImageResource(R.drawable.tab_fab_selected) // should be selected
                binding.btnMore.setImageResource(R.drawable.tab_more_n_selected)
            }

            "more" -> {
                binding.layoutHam.visibility = View.GONE
                binding.layoutMul.visibility = View.GONE
                binding.layoutSettings.visibility = View.VISIBLE
                binding.layoutBookmark.visibility = View.GONE
                binding.layoutNotification.visibility = View.GONE
                binding.btnHome.setImageResource(R.drawable.tab_home_n_selected)
                binding.btnSearch.setImageResource(R.drawable.tab_notification_n_selected)
                binding.btnVideo.setImageResource(R.drawable.tab_video_n_selected)
                binding.btnFab.setImageResource(R.drawable.tab_fav_n_selected)
                binding.btnMore.setImageResource(R.drawable.tab_more_selected)
            }
        }


    }
}