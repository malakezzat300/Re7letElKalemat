package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityAfterSuccessInGameBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityOnCityPressed2Binding

class AfterSuccessInGame : AppCompatActivity() {
    private lateinit var binding: ActivityAfterSuccessInGameBinding

    private lateinit var lottieAnimation: LottieAnimationView
    private lateinit var name : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterSuccessInGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottieAnimation = binding.congratesAnimation
        name = binding.personName
        name.text = FirebaseAuth.getInstance().currentUser?.displayName
        lottieAnimation.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({

            overridePendingTransition(R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_left)
            finish()
        }, 5000)
    }
}