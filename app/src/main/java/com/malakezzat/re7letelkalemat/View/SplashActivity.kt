package com.malakezzat.re7letelkalemat.View

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.ActivitySplashBinding

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
            finish()
        }, 5000)
    }}