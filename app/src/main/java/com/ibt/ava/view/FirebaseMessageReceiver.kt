package com.ibt.ava.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SplashActivity
import kotlin.random.Random


class FirebaseMessageReceiver : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        for(i in remoteMessage.data){
            Log.e("sdvsdvsdv",i.key+" "+i.value)
        }
        var link = remoteMessage.data["link"].toString()
        var image = remoteMessage.data["image"].toString()
        var title = remoteMessage.data["title"].toString()
        var body = remoteMessage.data["body"].toString()
        showNotification(
            title, body, link, image
        )
    }

    @SuppressLint("RemoteViewLayout")
    private fun getCustomDesign(
        title: String,
        message: String,image: String
    ): RemoteViews {
        val remoteViews = RemoteViews(
            applicationContext.packageName,
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.message, message)
        val futureTarget = Glide.with(applicationContext)
            .asBitmap()
            .load(Uri.parse(image))
            .submit()

        val bitmap = futureTarget.get()
        remoteViews.setImageViewBitmap(R.id.icon,bitmap)
        return remoteViews
    }

    // Method to display the notifications
    fun showNotification(
        title: String,
        message: String,link:String,image:String
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, SplashActivity::class.java)
        var s=link.split("/")
        Log.e("ascfsdvcsdvsd",s[s.size-1].toString()+"fhbfgnbfgb")
        intent.putExtra("id",s[s.size-1])
        // Assign channel ID
        val channel_id = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Pass the intent to PendingIntent to start the
        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        var SOUND_URI =
            Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.push);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channel_id
        ).setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.app_logo))
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true).setSound(SOUND_URI!!)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.
        builder = builder.setContent(
            getCustomDesign(title, message,image)
        )
        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(Random(1000000).nextInt(1000), builder.build())
    }
}
