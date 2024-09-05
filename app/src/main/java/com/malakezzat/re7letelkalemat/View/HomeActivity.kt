package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.R
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var db: ActivityHomeBinding
    lateinit var lottiePin :  LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(db.root)
        lottiePin = db.lottieAnimation1
        lottiePin .setOnClickListener{
            Toast.makeText(this, "Lottie Animation 1 clicked", Toast.LENGTH_SHORT).show()
        }
        /*findViewById<LottieAnimationView>(R.id.lottieAnimation1).setOnClickListener {

            Toast.makeText(this, "Lottie Animation 1 clicked", Toast.LENGTH_SHORT).show()
        }*/
    }

}