package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivitySaudiArabiaBinding

class SaudiArabiaActivity : AppCompatActivity() {
    lateinit var binding : ActivitySaudiArabiaBinding
    lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaudiArabiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this,R.raw.saudi_arabia_sound)

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.start()
        },1000)
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@SaudiArabiaActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 11500)

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}