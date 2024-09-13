package com.malakezzat.re7letelkalemat.View

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.malakezzat.re7letelkalemat.R
const val NOTIFICATION_ID = 9000
const val CHANNEL_ID = "ta3lm" // Make sure to use the actual channel ID used in NotificationChannel

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val intent = Intent(applicationContext, SplashActivity::class.java).apply {
        // Add any extra data if needed
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

// Create the PendingIntent
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        applicationContext,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.my_notif)
        .setContentTitle(applicationContext.getString(R.string.time_to_learn))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setColor(Color.GREEN)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}
