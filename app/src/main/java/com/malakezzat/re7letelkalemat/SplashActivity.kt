package com.malakezzat.re7letelkalemat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malakezzat.re7letelkalemat.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var db: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(db.root)

    }
}