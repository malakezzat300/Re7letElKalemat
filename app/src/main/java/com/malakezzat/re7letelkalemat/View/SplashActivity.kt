package com.malakezzat.re7letelkalemat.View


import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.animation.AlphaAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivitySplashBinding
import java.util.Calendar

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


            val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }, 5000)
        val intent = Intent()
        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        intent.data = Uri.parse("package:" + this.packageName)
        this.startActivity(intent)
        //scheduleDailyWork(this)
        if (getSharedPreferences("alert", MODE_PRIVATE).getBoolean("alert", true)) {
            scheduleDailyAlarm(this)
        }
    }

    fun scheduleDailyAlarm(context: Context) {
        getSharedPreferences("alert", MODE_PRIVATE).edit().putBoolean("alert", false).apply()
        val intent = Intent(context, MyBordCast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            159157,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)


        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY ,
            pendingIntent
        )

        val intent2 = Intent()
        intent2.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
        intent2.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent2)

        Log.d("AlarmManager", "Alarm set for 10 AM daily")
    }

}



