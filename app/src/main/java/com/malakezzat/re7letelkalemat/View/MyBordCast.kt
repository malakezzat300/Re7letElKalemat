package com.malakezzat.re7letelkalemat.View

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.malakezzat.re7letelkalemat.R

class MyBordCast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "3333 work start")
        if (!isAppInForeground(p0!!)) {
            createChannel(p0, "ta3lm", "ta3lm")
            val notificationManager =
                p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.sendNotification(p0.getString(R.string.learn_today), p0)
        }
        }
    private fun createChannel(context: Context,channelId: String, channelName: String) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,

            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)
        notificationChannel.description = context.getString(R.string.learn_today)
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
    fun isAppInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = context.packageName

        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && appProcess.processName == packageName) {
                return true
            }
        }
        return false
    }
}