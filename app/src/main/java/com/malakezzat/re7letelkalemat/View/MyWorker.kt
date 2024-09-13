import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.View.DailyNotificationWorker
import com.malakezzat.re7letelkalemat.View.sendNotification
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MyWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
        const val NOTIFICATION_ID = 9000
        const val CHANNEL_ID = "ta3lm"
    }

    override fun doWork(): Result {
        // Get the NotificationManager from the context
        Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "worker start")
        createChannel(CHANNEL_ID, context.getString(R.string.learn_today))
        val workRequest = OneTimeWorkRequestBuilder<DailyNotificationWorker>()
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "DailyNotificationWork",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
        return Result.success()
    }
    private fun calculateInitialDelay(): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // If the time has already passed for today, set for tomorrow
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return calendar.timeInMillis - System.currentTimeMillis()
    }
    private fun createChannel(channelId: String, channelName: String) {

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
}
