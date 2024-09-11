package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed2Binding


class OnCityPressed2 : AppCompatActivity() {
    private lateinit var binding: ActivityOnCityPressed2Binding
    private var isActivityRunning = true
    private lateinit var lottieAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnCityPressed2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        lottieAnimation = binding.animationCity2
        lottieAnimation.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            if(isActivityRunning)
            {
                val intent = Intent(this@OnCityPressed2, SaudiArabiaActivity::class.java)

                startActivity(intent)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)

                finish()
            }
        }, 4300)

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityRunning = false
    }

}