package com.ibt.ava

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.dagger.component.AppComponent
import com.ibt.ava.dagger.component.DaggerAppComponent
import com.ibt.ava.dagger.module.ContextModule
import com.ibt.ava.dagger.module.RetrofitModule
import com.ibt.ava.util.Helper
import java.security.Security
import java.util.*
import com.ibt.ava.service.APIInterface
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.user.Bookmark
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.lang.reflect.Type
import javax.inject.Inject
import kotlin.collections.ArrayList


/*
* Custom Application class for global tasks
* */
class AppController : Application() {

    lateinit var component: AppComponent

    var isInternetAvailable = true
    lateinit var mGoogleSignInClient: GoogleSignInClient

    @Inject
    lateinit var api: APIInterface
    override fun onCreate() {
        super.onCreate()
        /**
         * getting shared preference instance
         */
        prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        //  setNightModeEnabled(true)
        setNightMode(isNightModeEnabled())
        /**
         * building dagger component using retrofit module and application context
         * injecting into the app controller
         */
        component = DaggerAppComponent.builder().retrofitModule(RetrofitModule())
            .contextModule(ContextModule(applicationContext)).build()
        component.inject(this@AppController)

        /**
         * setting up bouncy castle for supporting ECDSA
         */
        setupBouncyCastle()

        /**
         * checking internet availability on every 3 seconds interval
         */
        checkInternetAvailablity()
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId().requestProfile().requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(applicationContext, gso);

    }


    companion object {
        private lateinit var prefs: SharedPreferences
        private val NOTIFICATION_TOPIC = "ava_genaral_users"

        /**
         * getting app controller instance
         */
        fun get(context: Activity): AppController {
            return context.application as AppController
        }


        public fun setNightMode(state: Boolean) {
            if (state) {
                setNightModeEnabled(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                setNightModeEnabled(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }

        /**
         * get night mode setting from preferences
         */
        fun isNightModeEnabled(): Boolean {
            return prefs.getBoolean("enable_night_mode", false)

        }

        fun setNightModeEnabled(enabled: Boolean) {
            prefs.edit().putBoolean("enable_night_mode", enabled).apply();
        }

        fun getNotificationEnabled(): Boolean {
            return prefs.getBoolean("notificationEnabled", false)
        }

        fun setNotificationEnabled(enabled: Boolean) {
            prefs.edit().putBoolean("notificationEnabled", enabled).apply();
            if (enabled) {
                FirebaseMessaging.getInstance().subscribeToTopic(NOTIFICATION_TOPIC)
                    .addOnSuccessListener {

                    }
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(NOTIFICATION_TOPIC)
                    .addOnSuccessListener {

                    }
            }
        }

        fun setFirstTime(status: Boolean) {
            prefs.edit().putBoolean("firstTime", status).apply();
        }

        fun getFirstTime(): Boolean {
            return prefs.getBoolean("firstTime", false)
        }


        /**
         * get biometric lock setting from preferences
         */
        fun isBiometricEnabled(): Boolean {
            return prefs.getBoolean("enable_fingerprint", false)

        }

        fun loggedIn(): Boolean {
            return prefs.getBoolean("logged_in", false)
        }

        fun loggedIn(status: Boolean) {
            var editor = prefs.edit()
            editor.putBoolean("logged_in", status)
            editor.apply()
        }

        /**
         * get biometric lock setting from preferences
         */
        fun clearCache(): Boolean {
            try {
                var editor = prefs.edit()
                editor.clear()
                editor.apply()
            } catch (ex: Exception) {

            }
            return true
        }

        fun setUserData(value: String) {
            var editor = prefs.edit()
            editor.putString("userdata", value)
            editor.apply()

            Log.e("sdvsdvsdfddddd", value)
            Log.e("vdjknvdnjdvn", prefs.getString("userdata", "").toString())
        }


        fun getUserData(): String? {
            return prefs.getString(
                "userdata", ""
            )
        }

        fun setBookmarks(data: ArrayList<Bookmark>) {
            var editor = prefs.edit()
            editor.putString("bookmarks", Gson().toJson(data))
            editor.apply()
        }


        fun getBookmarks(): ArrayList<Bookmark> {
            var d = prefs.getString("bookmarks", "")
            if (d.isNullOrEmpty()) {
                return ArrayList<Bookmark>()
            }
            val type: Type = object : TypeToken<List<Bookmark?>?>() {}.type
            var datas: List<Bookmark> = Gson().fromJson(d, type)
            return ArrayList<Bookmark>(datas);
        }
    }


    /**
     * get app component instance
     */
    fun getApplicationComponent(): AppComponent {
        return component
    }


    /**
     * checking internet on every 3 seconds using java runtime with pinging to google.
     */
    fun checkInternetAvailablity() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                try {
                    val process = Runtime.getRuntime().exec("ping -c 3 www.google.com")
                    val returnVal = process.waitFor()
                    val reachable = (returnVal == 0)
                    if (!isInternetAvailable.equals(reachable)) {
                        isInternetAvailable = reachable
                        Helper.isInternetAvailable.postValue(reachable)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }, 0, 3000)
    }


    /**
     * setting up bouncycastle globally to support bouncy castle with ECDSA
     */
    private fun setupBouncyCastle() {
        val provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up the provider lazily when it's first used.
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            // BC with same package name, shouldn't happen in real life.
            return
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }
}