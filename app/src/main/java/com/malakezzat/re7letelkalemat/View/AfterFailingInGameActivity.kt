package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityAfterFailingInGameBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding

class AfterFailingInGameActivity : AppCompatActivity() {

    lateinit var db: ActivityAfterFailingInGameBinding
    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var mediaPlayer: MediaPlayer
    private var isActivityRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityAfterFailingInGameBinding.inflate(layoutInflater)
        setContentView(db.root)
        lottieAnimation = db.viewAnimator
        mediaPlayer = MediaPlayer.create(this, R.raw.after_failing_in_game)
    }

    override fun onResume() {
        super.onResume()

        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                mediaPlayer.start()
                lottieAnimation.loop(true)
                lottieAnimation.playAnimation()
            }
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isActivityRunning) {
                lottieAnimation.cancelAnimation()
                val intent = Intent(this@AfterFailingInGameActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 8000)
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
        mediaPlayer.release()
    }
}