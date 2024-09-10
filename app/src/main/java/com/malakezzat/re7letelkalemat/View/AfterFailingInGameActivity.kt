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
    private var length : Int = 0
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
                mediaPlayer.seekTo(length)
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
        }, mediaPlayer.duration.toLong() + 1000)
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