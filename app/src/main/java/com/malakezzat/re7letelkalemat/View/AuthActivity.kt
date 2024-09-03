package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malakezzat.re7letelkalemat.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var db: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(db.root)

    }
}