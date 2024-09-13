package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    private var length : Int = 0
    lateinit var city : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityFirstGameRulsBinding.inflate(layoutInflater)
        setContentView(db.root)
        city = intent.getStringExtra("city") ?: "medina"

        Log.i("TAG", "onCreate: $city")
        animation = db.viewAnimator
        startNowBtn = db.startNow
        if(city == "mecca"){
            mediaPlayer = MediaPlayer.create(this, R.raw.start_first_game)
        }else if(city == "medina"){
            mediaPlayer = MediaPlayer.create(this, R.raw.game2_rules)
        }

        startNowBtn.setOnClickListener {
            var intent = Intent()
            if(city == "mecca"){
                intent = Intent(this, RearrangeWordGameActivity::class.java)
            }else if(city == "medina"){
                intent = Intent(this, FindTheMeaningGame::class.java)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()

        // Add a delay before starting the media player and animation
        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                mediaPlayer.seekTo(length)
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
        }, mediaPlayer.duration.toLong() + 2000) // 10.5 seconds total delay
    }

    override fun onPause() {
        super.onPause()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            length = mediaPlayer.currentPosition
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }

}