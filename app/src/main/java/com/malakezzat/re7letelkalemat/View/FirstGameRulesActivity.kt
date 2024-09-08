package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityFirstGameRulsBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityRewordFinishCityWordsBinding

class FirstGameRulesActivity : AppCompatActivity() {
    lateinit var db : ActivityFirstGameRulsBinding
    private lateinit var animation: LottieAnimationView
    private lateinit var startNowBtn: Button
    private lateinit var mediaPlayer: MediaPlayer
    private var isActivityRunning = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityFirstGameRulsBinding.inflate(layoutInflater)
        setContentView(db.root)
        animation = db.viewAnimator
        startNowBtn = db.startNow
        mediaPlayer = MediaPlayer.create(this, R.raw.start_first_game)
        startNowBtn.setOnClickListener {
            val intent = Intent(this, RearrangeWordGameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()

        // Add a delay before starting the media player and animation
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                mediaPlayer.start()
                animation.loop(true)
                animation.playAnimation()
            }
        }, 2000) // 2-second delay

        // Delay for transitioning to the HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                animation.cancelAnimation()
            }
        }, 3500) // 10.5 seconds total delay
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
        mediaPlayer.release()
    }
}