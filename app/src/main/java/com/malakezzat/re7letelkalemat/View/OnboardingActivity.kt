package com.malakezzat.re7letelkalemat.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private val layouts = listOf(
        R.layout.onboarding_screen_1,
        R.layout.onboarding_screen_2,
        R.layout.onboarding_screen_3
    )

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get SharedPreferences
        val sharedPreferences = getSharedPreferences("onboarding", MODE_PRIVATE)
        val isOnboardingCompleted = sharedPreferences.getBoolean("completed", false)

        // Check if onboarding is already completed
        if (isOnboardingCompleted) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        // Set up ViewPager with OnboardingAdapter
        adapter = OnboardingAdapter(layouts)
        binding.viewPager.adapter = adapter

        // Set up TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        // Handle Next Button
        binding.buttonNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < layouts.size - 1) {
                // Move to next page
                binding.viewPager.currentItem = current + 1
            } else {
                // Mark onboarding as completed in SharedPreferences
                sharedPreferences.edit().putBoolean("completed", true).apply()

                // Navigate to AuthActivity after onboarding is completed
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }
}