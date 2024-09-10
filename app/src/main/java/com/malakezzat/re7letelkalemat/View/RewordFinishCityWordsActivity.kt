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
import com.malakezzat.re7letelkalemat.databinding.ActivityRewordFinishCityWordsBinding

class RewordFinishCityWordsActivity : AppCompatActivity() {
    private lateinit var db: ActivityRewordFinishCityWordsBinding
    private lateinit var splashAnimation: LottieAnimationView
    private lateinit var splashAnimation2: LottieAnimationView
    private lateinit var button1: Button
    private lateinit var button2: Button

    private lateinit var mediaPlayer: MediaPlayer
    private var isActivityRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityRewordFinishCityWordsBinding.inflate(layoutInflater)
        setContentView(db.root)
        splashAnimation = db.viewAnimator
        splashAnimation2 = db.viewAnimator2
        button1 = db.ready
        button2 = db.secondButton
        mediaPlayer = MediaPlayer.create(this, R.raw.ready)

        button1.setOnClickListener{
            val intent = Intent(this, FirstGameRulesActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }
        button2.setOnClickListener{
            //go to my cards
            val intent = Intent(this, OnCityPressed2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_pop_in, R.anim.fragment_slide_out_left)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()

        // Add a delay before starting the media player and animation
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                mediaPlayer.start()
                splashAnimation.loop(true)
                splashAnimation.playAnimation()
                splashAnimation2.loop(true)
                splashAnimation2.playAnimation()
            }
        }, 1000) // 2-second delay

        // Delay for transitioning to the HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                splashAnimation.cancelAnimation()
            }
        }, 7500) // 10.5 seconds total delay
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
        mediaPlayer.release()
    }
}