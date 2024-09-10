package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed1Binding

class onCityPressed1 : AppCompatActivity() {
    private lateinit var db: ActivityOnCityPressed1Binding
    private lateinit var splashAnimation: LottieAnimationView
    private lateinit var mediaPlayer: MediaPlayer
    private var isActivityRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityOnCityPressed1Binding.inflate(layoutInflater)
        setContentView(db.root)
        splashAnimation = db.viewAnimator
        mediaPlayer = MediaPlayer.create(this, R.raw.mosta3ed)
    }

    override fun onResume() {
        super.onResume()

        // Add a delay before starting the media player and animation
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                mediaPlayer.start()
                splashAnimation.loop(true)
                splashAnimation.playAnimation()
            }
        }, 1000) // 2-second delay

        // Delay for transitioning to the HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                splashAnimation.cancelAnimation()
                val intent = Intent(this@onCityPressed1, OnCityPressed2::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                finish()
            }
        }, 9500) // 10.5 seconds total delay
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
        mediaPlayer.release()
    }
}
