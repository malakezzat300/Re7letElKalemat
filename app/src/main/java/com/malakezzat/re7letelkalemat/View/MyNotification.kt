package com.malakezzat.re7letelkalemat.View

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.malakezzat.re7letelkalemat.R

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.app_name))     .setSmallIcon(R.drawable.girl2)
        .setContentTitle(applicationContext
            .getString(R.string.learn_today))
        .setContentText(messageBody)
    val notificationManager = ContextCompat.getSystemService(
        applicationContext,
        NotificationManager::class.java
    ) as NotificationManager
//    notify(NOTIFICATION_ID, builder.build())

}
