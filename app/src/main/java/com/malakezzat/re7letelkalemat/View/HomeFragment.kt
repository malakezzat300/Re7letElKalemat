package com.malakezzat.re7letelkalemat.View

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var db: FragmentHomeBinding
    private lateinit var lottiePin: LottieAnimationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FragmentHomeBinding.inflate(inflater)
        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottiePin = db.lottieAnimation1
        lottiePin.setOnClickListener {
            val intent = Intent(requireContext(), onCityPressed1::class.java)
            startActivity(intent)
        }
    }
}