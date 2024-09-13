package com.malakezzat.re7letelkalemat.View

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.malakezzat.re7letelkalemat.R
const val NOTIFICATION_ID = 9000
const val CHANNEL_ID = "ta3lm" // Make sure to use the actual channel ID used in NotificationChannel

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "notification work start")

    val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)  // Use the actual channel ID
        .setSmallIcon(R.drawable.girl2)
        .setContentTitle(applicationContext.getString(R.string.learn_today))
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}
