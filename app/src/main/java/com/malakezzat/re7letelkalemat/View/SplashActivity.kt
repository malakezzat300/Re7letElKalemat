package com.malakezzat.re7letelkalemat.View


import android.animation.Animator
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.CalendarContract
import android.provider.Settings
import android.util.Log
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    lateinit var db: ActivitySplashBinding
    lateinit var splashAnimation : LottieAnimationView
    lateinit var appTitle : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(db.root)
        splashAnimation = db.viewAnimator
        appTitle = db.appTitle

        splashAnimation.loop(true)
        splashAnimation.playAnimation()

        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1000
        fadeIn.startOffset = 1000

        Handler(Looper.getMainLooper()).postDelayed({
            appTitle.visibility = TextView.VISIBLE
            appTitle.startAnimation(fadeIn)
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({

            splashAnimation.cancelAnimation()


            val intent = Intent(this@SplashActivity, AuthActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }, 5000)
        val intent = Intent()
        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        intent.data = Uri.parse("package:" + this.packageName)
        this.startActivity(intent)
       scheduleDailyWork(this)


    }
    fun scheduleDailyWork(context: Context) {
//        val workManager = WorkManager.getInstance(context)
//
//        Log.d("scheduleDailyWork", "Creating and Enqueuing WorkManager")
//
//        // Create a PeriodicWorkRequest to execute the worker every 24 hours
//        val dailyWorkRequest = PeriodicWorkRequestBuilder<MyDailyNotificationJobService>(1, TimeUnit.DAYS)
//            .setInitialDelay(0, TimeUnit.MILLISECONDS) // Start the worker immediately
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            "DailyWork",
//            ExistingPeriodicWorkPolicy.REPLACE,  // Replaces any existing periodic work with the same name
//            dailyWorkRequest
//        )
    }


}
