package com.malakezzat.re7letelkalemat.View

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.malakezzat.re7letelkalemat.R
import com.google.firebase.auth.FirebaseAuth
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var db: ActivityHomeBinding
    private lateinit var navController: NavController
    lateinit var  bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(db.root)
        navController = findNavController(R.id.nav_host_home_fragment)
        bottomNavigationView = db.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

}