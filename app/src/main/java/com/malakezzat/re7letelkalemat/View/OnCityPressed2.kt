package com.malakezzat.re7letelkalemat.View

import android.animation.Animator
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed2Binding


class OnCityPressed2 : AppCompatActivity() {
    private lateinit var binding: ActivityOnCityPressed2Binding
    private  val TAG = "OnCityPressed2"
    private lateinit var lottieAnimation: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnCityPressed2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        lottieAnimation = binding.animationCity2
        lottieAnimation.playAnimation()
        lottieAnimation.addAnimatorListener(object :  Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {

            }

            override fun onAnimationEnd(p0: Animator) {
                val intent = Intent(this@OnCityPressed2, SaudiArabiaActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
                finish()
            }

            override fun onAnimationCancel(p0: Animator) {

            }

            override fun onAnimationRepeat(p0: Animator) {
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.animationCity2.cancelAnimation()
    }

    override fun onResume() {
        super.onResume()
        binding.animationCity2.resumeAnimation()
    }
    override fun onPause() {
        super.onPause()
        binding.animationCity2.pauseAnimation()

    }
}