package com.malakezzat.re7letelkalemat

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malakezzat.re7letelkalemat.View.HomeActivity
import com.malakezzat.re7letelkalemat.databinding.ActivityMeccaBinding

class Mecca : AppCompatActivity() {
    lateinit var binding: ActivityMeccaBinding
    lateinit var mediaPlayer : MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMeccaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this,R.raw.mecca)

        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.start()
        },1000)
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@Mecca, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 9500)
    }
}