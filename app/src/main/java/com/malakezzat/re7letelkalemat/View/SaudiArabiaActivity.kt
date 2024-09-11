package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivitySaudiArabiaBinding

class SaudiArabiaActivity : AppCompatActivity() {
    lateinit var binding : ActivitySaudiArabiaBinding
    lateinit var mediaPlayer : MediaPlayer

    private var length : Int = 0
    private var isSaudiArabiaActivityRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaudiArabiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this,R.raw.saudi_arabia_sound)

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
            //if(isSaudiArabiaActivityRunning){
            //    mediaPlayer.start()
            //}
        },1000)
        Handler(Looper.getMainLooper()).postDelayed({
            if(isSaudiArabiaActivityRunning){
                val intent = Intent(this@SaudiArabiaActivity, MeccaActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                finish()
            }

            val intent = Intent(this@SaudiArabiaActivity, MeccaActivity::class.java)
            startActivity(intent)
            finish()
        }, mediaPlayer.duration.toLong() + 1000)

    }


    override fun onPause() {
        super.onPause()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            length = mediaPlayer.currentPosition
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
        isSaudiArabiaActivityRunning=false
    }
    
    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
        isSaudiArabiaActivityRunning=false
    }

}