package com.malakezzat.re7letelkalemat.View

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var db: FragmentHomeBinding
    private lateinit var lottiePin: LottieAnimationView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f // Initial scale is 1 (original size)
    private var scaleAnimator: ValueAnimator? = null
    private var scaleX = 1.0f
    private var scaleY = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FragmentHomeBinding.inflate(inflater)

        // Initialize ScaleGestureDetector
        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())

        return db.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottiePin = db.lottieAnimation1
        lottiePin.setOnClickListener {
            val intent = Intent(requireContext(), onCityPressed1::class.java)
            startActivity(intent)
        }

        // Apply scaling on the background (ConstraintLayout)
        db.root.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        private var scaleFocusX = 0f
        private var scaleFocusY = 0f

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            scaleFocusX = detector.focusX
            scaleFocusY = detector.focusY
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            val minScaleFactor = 1.0f
            val maxScaleFactor = 3.5f
            val newScaleFactor = (this@HomeFragment.scaleFactor * scaleFactor).coerceIn(minScaleFactor, maxScaleFactor)

            if (scaleAnimator == null) {
                scaleAnimator = ValueAnimator.ofFloat(scaleX, newScaleFactor).apply {
                    duration = 500
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { animation ->
                        val animatedScale = animation.animatedValue as Float
                        // Update scale around the focal point
                        db.root.pivotX = scaleFocusX
                        db.root.pivotY = scaleFocusY
                        db.root.scaleX = animatedScale
                        db.root.scaleY = animatedScale
                    }
                }
            } else {
                // Update the existing ValueAnimator with new values
                scaleAnimator?.apply {
                    setFloatValues(scaleX, newScaleFactor)
                    start()
                }
            }

            // Update scale factors
            this@HomeFragment.scaleFactor = newScaleFactor
            scaleX = newScaleFactor
            scaleY = newScaleFactor

            return true
        }
    }
}
