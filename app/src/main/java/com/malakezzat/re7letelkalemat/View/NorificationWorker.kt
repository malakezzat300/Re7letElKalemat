package com.malakezzat.re7letelkalemat.View

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.malakezzat.re7letelkalemat.R

class DailyNotificationWorker (val appContext: Context, workerParams: WorkerParameters) :Worker(appContext,workerParams) {
    override fun doWork(): Result {
        Log.d("efffffffffffffaaaaaff", "notification work start")
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.sendNotification(appContext.getString(R.string.learn_today), appContext)
        return Result.success()
    }
}
