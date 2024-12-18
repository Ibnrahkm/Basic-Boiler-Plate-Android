package com.ibt.ava.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.service.model.StaticCategory
import org.ocpsoft.prettytime.PrettyTime
import java.math.BigDecimal
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.Executor

/*
* Helper class
* */
class Helper {


    companion object {
        /**
         * all static variables and functions
         */
        public var isInternetAvailable = MutableLiveData<Boolean>()

        private var progressDialog: Dialog? = null
        private lateinit var alertDialog: AlertDialog.Builder

        /**
         * for checking input fields emptiness and show message
         * accordingly
         */
        fun checkInputFields(activity: Activity, editText: Array<EditText>): Boolean {
            var counter: Int = 0;
            for (singleEditText: EditText in editText) {
                if (singleEditText.text.toString().trim()
                        .equals("") || singleEditText.text.toString().trim().equals(
                        "null"
                    )
                ) {
                    Toast.makeText(
                        activity,
                        singleEditText.tag.toString() + " " + activity.getString(R.string.is_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                } else {
                    if (singleEditText.tag.equals(activity.getString(R.string.email))) {
                        val emailPattern = "[a-zA-Z0-9+._-]+@[a-z]+\\.+[a-z]+"
                        val inputEmail = singleEditText.text.toString().trim()
                        if (!inputEmail.matches(Regex(emailPattern))) {
                            Toast.makeText(
                                activity,
                                singleEditText.tag.toString()
                                    .trim() + activity.getString(R.string.is_empty),
                                Toast.LENGTH_SHORT
                            ).show()
                            return false
                        } else {
                            counter++
                        }
                    } else {
                        counter++
                    }
                }
            }
            return counter == editText.size;
        }

        fun prepareStaticCategoryData(): ArrayList<StaticCategory> {
            var list = ArrayList<StaticCategory>()
            list.add(StaticCategory("", "سەرەکی"))
            list.add(StaticCategory("7", "کوردستان"))
            list.add(StaticCategory("8", "عێراق"))
            list.add(StaticCategory("20", "ڕۆژهەڵاتی ناوەڕاست"))
            list.add(StaticCategory("9", "جیهان"))
            list.add(StaticCategory("13", "ڕاپۆرت"))
            list.add(StaticCategory("12", "بیروڕا"))
            list.add(StaticCategory("11", "بزنس"))
            //list.add(StaticCategory("16", "ئاڤا TV"))
            list.add(StaticCategory("17", "ببینە و ببیستە"))
            list.add(StaticCategory("14", "کولتوور"))
            list.add(StaticCategory("15", "ژینگە"))
            list.add(StaticCategory("21", "تەندروستی"))
            return list
        }


        fun openYoutube(key: String, context: Activity) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=$key")
                )
            )
        }

        /**
         * set image using glide on custom binding adapter
         */
        @JvmStatic
        @BindingAdapter("app:src")
        fun setImage(view: ImageView, url: Any?) {
            if (url != null) {
                Log.e("sdvsdvsdvs", url.toString())
                Glide.with(view.context).load(url).into(view)
            }
        }

        fun setImagePromatcally(view: ImageView, url: Any?) {
            if (url != null) {
                Log.e("sdvsdvsdvs", url.toString())
                if (view.id==R.id.iv_author){
                    Glide.with(view.context).load(url).placeholder(R.drawable.ava_link).into(view)
                }else{
                    Glide.with(view.context).load(url).into(view)
                }
            }
        }


        fun convertEpochToHour(epoch: Long): String {
            val date: Date = Date(epoch * 1000)
            val sdf: SimpleDateFormat = SimpleDateFormat("hh:mm aa")
            val formattedTime: String = sdf.format(date)
            return formattedTime
        }

        /**
         * show alert dialog
         */
        fun showAlertDialog(
            code: Int,
            title: String,
            message: String,
            context: Activity
        ): AlertDialog {
            alertDialog = AlertDialog.Builder(context,R.style.AlertDialogCustom)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)
            alertDialog.setNeutralButton(
                context.getString(R.string.okay),
                DialogInterface.OnClickListener { a, v ->
                    alertDialog.setCancelable(true)
                })

            var dialog = alertDialog.create()
            dialog.show()
            return dialog!!
        }


        /**
         * show alert dialog
         */
        fun showDecisionDialog(
            posButtonText: String,
            negButtonText: String,
            title: String,
            message: String,
            context: Activity, res: MutableLiveData<Boolean>
        ): AlertDialog.Builder {
            alertDialog = AlertDialog.Builder(context,R.style.AlertDialogCustom)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton(
                posButtonText,
                DialogInterface.OnClickListener { a, v ->
                    alertDialog.setCancelable(true)
                    res.value = true
                })

            alertDialog.setNegativeButton(
                negButtonText,
                DialogInterface.OnClickListener { a, v ->
                    alertDialog.setCancelable(true)
                    res.value = false
                })
            alertDialog.create().show()
            return alertDialog
        }


        /**
         * format number with decimal
         */
        fun formatNumber(number: String, type: String): String {
            return try {
                if (number.toFloat() == 0f) {
                    formatNumber("0.0".toFloat(), 2)
                } else {
                    var dp: Int
                    if (type == "USD") {
                        dp = 2
                    } else {
                        dp = 8
                    }
                    if (number.contains(".")) {
                        formatNumber(number.toFloat(), dp)
                    } else {
                        formatNumber("$number.0".toFloat(), dp)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                number
            }
        }


        /**
         * format number with decimal point
         */
        private fun formatNumber(float: Float, dp: Int): String {
            return String.format("%.${dp}f", float)
        }


        /**
         * show info dialog
         */
        fun showInfoDialog(context: Activity, message: String): Dialog {
            val dialog = Dialog(context,R.style.AlertDialogCustom)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_info_dialog)
            dialog.findViewById<TextView>(R.id.tv_details).text = message
            dialog.findViewById<TextView>(R.id.btn_dismiss)
                .setOnClickListener { v ->
                    dialog.dismiss()
                }
            dialog.show()
            return dialog
        }

        /**
         * show progress dialog
         */
        fun showProgressDialog(title: String, message: String, context: Activity): Dialog {
            if (progressDialog == null || !progressDialog!!.isShowing) {
                progressDialog = Dialog(context)
                progressDialog!!.setContentView(R.layout.layout_progress)
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                Glide.with(context).asGif().load(R.drawable.loading)
                    .into(progressDialog!!.findViewById(R.id.image))
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            }

            return progressDialog!!
        }


        /**
         * hide progress dialog
         */
        fun hideProgressDialog() {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }

        fun formatDateEEEE(dateString: String): String {
            return try {
                val p = PrettyTime()
                val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
                val utcDateTime = ZonedDateTime.parse(dateString, formatter)
                val userTimeZone = ZoneId.systemDefault()
                val userDateTime = utcDateTime.withZoneSameInstant(userTimeZone)
                p.format(userDateTime)
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                ""
            }
        }

        /**
         * modify status bar according to needs
         * true means white
         * false means holo green light
         * else color int
         */
        fun handleStatusBar(window: Window, statusBar: Any, context: Activity) {
            if (statusBar is Boolean && statusBar) {
                window.statusBarColor =
                    ContextCompat.getColor(context, R.color.default_status_bar_color)
                val decor = window.decorView
                if (AppController.isNightModeEnabled()) {
                    decor.systemUiVisibility = 0
                } else {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } else if (statusBar is Int) {
                window.statusBarColor = statusBar
                val decor = window.decorView
                if (statusBar == Color.WHITE) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decor.systemUiVisibility = 0
                }
            }
        }

        fun convertCryptoToBigInteger(amount: String, decimals: String): String {


            val factor = BigDecimal.TEN.pow(decimals.toInt())
            val amountBigInteger: BigInteger = BigDecimal(amount).multiply(factor).toBigInteger()


            return amountBigInteger.toString()
        }

        fun convertBigIntegerToCrypto(amount: String, decimals: String): String {

            val factor = BigDecimal.TEN.pow(decimals.toInt())
            val amountBigInteger: BigDecimal = BigDecimal(amount).divide(factor)
            return amountBigInteger.toPlainString()
        }

        /**
         * format eth amount
         */
        fun formatEthNumber(ethAmount: String): String {
            return "$ethAmount"// ETH"
        }

        /**
         * format eth amount
         */
        fun formatEthNumber(ethAmount: String, symbol: String): String {
            return "$ethAmount ${symbol.toUpperCase()}"
        }

        /**
         * format Gwei amount
         */
        fun formatGWeiNumber(gweiNumber: String): String {
            return "$gweiNumber GWEI"
        }


        /**
         * calculate usd amount
         */
        fun calculateUSDNumber(ethAmount: String, usdPrice: String): String {
            return try {
                formatUSDAmount((ethAmount.toFloat() * usdPrice.toFloat()).toString())
            } catch (ex: Exception) {
                "0.00"// \u0024"
            }
        }

        /**
         * format usd amount
         */
        fun formatUSDAmount(usdAmount: String): String {
            return formatNumber(usdAmount, "USD") + " \u0024"
        }


        /**
         * toggle theme
         */
        fun toogleTheme(nightMode: Boolean, activity: Activity, forceRestart: Boolean) {
            if (nightMode) {
                activity.getTheme().applyStyle(R.style.ThemeNightModeCustom, true)
            } else {
                activity.getTheme().applyStyle(R.style.ThemeDayModeCustom, true)
            }
            if (forceRestart) {
                activity.finish()
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        activity.javaClass
                    )
                )
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        /**
         * biometric lock dialog
         */
        fun showBiometricDialog(
            popupTitle: String,
            popupSubTitle: String,
            negativeButtonText: String, prompt: BiometricPrompt
        ) {
            var promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(popupTitle)
                .setSubtitle(popupSubTitle)
                .setDeviceCredentialAllowed(true)
                .setConfirmationRequired(true)
                .build()
            try {
                prompt.authenticate(promptInfo)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }


        /**
         * init biometric lock
         */
        fun initBitmetric(
            context: AppCompatActivity,
            callback: BiometricPrompt.AuthenticationCallback
        ): BiometricPrompt {
            var executor: Executor = ContextCompat.getMainExecutor(context)
            return BiometricPrompt(context, executor, callback)
        }


        /**
         * check biometric lock availablity on device
         */
        fun checkDeviceBiometricAvailablity(context: AppCompatActivity): Boolean {
            val biometricManager = BiometricManager.from(context)
            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                    return true
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Log.e("MY_APP_TAG", "No biometric features available on this device.")

                    return false
                }

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                    return false
                }

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Log.e(
                        "MY_APP_TAG", "The user hasn't associated " +
                                "any biometric credentials with their account."
                    )
                    return true
                }

            }
            return false
        }

        /**
         * handle internet availability
         */
        fun handleInternet(context: AppCompatActivity, binding: ViewDataBinding) {
            var s: Snackbar? = null
            isInternetAvailable.observe(context, Observer {
                if (it != null)
                    if (!it) {
                        if (s == null || !s!!.isShown) {
                            s = Snackbar.make(
                                binding.root,
                                context.getString(R.string.you_are_offline),
                                Snackbar.LENGTH_INDEFINITE
                            )
                            s!!.show()
                        }
                    } else {
                        if (s != null && s!!.isShown) {
                            s!!.setText(context.getString(R.string.you_are_online))
                            Handler().postDelayed(Runnable {
                                s!!.dismiss()
                            }, 500)
                        }
                    }
            })
        }
    }
}