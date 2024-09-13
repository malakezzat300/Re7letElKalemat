package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.malakezzat.re7letelkalemat.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        /*val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = OnboardingPagerAdapter(this)
        viewPager.adapter = adapter
    }

    fun navigateToNextPage() {
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.currentItem = viewPager.currentItem + 1
    }*/

    fun startMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
}