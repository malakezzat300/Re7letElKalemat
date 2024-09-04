package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var db: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(db.root)
    }
}