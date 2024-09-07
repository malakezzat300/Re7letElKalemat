package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed2Binding


class OnCityPressed2 : AppCompatActivity() {
    private lateinit var binding: ActivityOnCityPressed2Binding

    private lateinit var lottieAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnCityPressed2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        lottieAnimation = binding.animationCity2
        lottieAnimation.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@OnCityPressed2, SaudiArabiaActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }

    override fun onResume() {
        super.onResume()



    }
}