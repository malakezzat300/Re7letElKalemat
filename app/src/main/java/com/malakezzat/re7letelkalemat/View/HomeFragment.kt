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
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f // Initial scale is 1 (original size)
    private var scaleAnimator: ObjectAnimator? = null
    private var scaleX = 1.0f
    private var scaleY = 1.0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

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

        // Apply scaling and scrolling on the background (ConstraintLayout)
        db.root.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            handleDragging(event)
            true
        }
    }

    private fun handleDragging(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                isDragging = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    val deltaX = event.x - lastTouchX
                    val deltaY = event.y - lastTouchY

                    val newTranslationX = db.root.translationX + deltaX
                    val newTranslationY = db.root.translationY + deltaY

                    val maxTranslationX = calculateMaxTranslationX()
                    val maxTranslationY = calculateMaxTranslationY()

                    db.root.translationX = newTranslationX.coerceIn(-maxTranslationX, maxTranslationX)
                    db.root.translationY = newTranslationY.coerceIn(-maxTranslationY, maxTranslationY)

                    lastTouchX = event.x
                    lastTouchY = event.y
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
            }
        }
    }

    private fun calculateMaxTranslationX(): Float {
        val drawableWidth = (db.root.background?.intrinsicWidth ?: 0) * scaleX
        val viewWidth = db.root.width
        return maxOf(0f, drawableWidth - viewWidth)
    }

    private fun calculateMaxTranslationY(): Float {
        val drawableHeight = (db.root.background?.intrinsicHeight ?: 0) * scaleY
        val viewHeight = db.root.height
        return maxOf(0f, drawableHeight - viewHeight)
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

            // Use ObjectAnimator for smoother animation
            if (scaleAnimator == null) {
                scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(
                    db.root,
                    PropertyValuesHolder.ofFloat("scaleX", scaleX, newScaleFactor),
                    PropertyValuesHolder.ofFloat("scaleY", scaleY, newScaleFactor)
                ).apply {
                    duration = 300
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { animation ->
                        // Update pivotX and pivotY to be the focal point
                        db.root.pivotX = scaleFocusX
                        db.root.pivotY = scaleFocusY
                    }
                }
            } else {
                scaleAnimator?.apply {
                    setValues(
                        PropertyValuesHolder.ofFloat("scaleX", scaleX, newScaleFactor),
                        PropertyValuesHolder.ofFloat("scaleY", scaleY, newScaleFactor)
                    )
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