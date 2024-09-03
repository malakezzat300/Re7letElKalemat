package com.malakezzat.re7letelkalemat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.malakezzat.re7letelkalemat.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var db: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(db.root)

    }
}