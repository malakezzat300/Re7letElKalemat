package com.malakezzat.re7letelkalemat.View

import android.content.Intent
import android.graphics.Matrix
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var lottiePin: LottieAnimationView
    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private val matrix = Matrix()
    private var scaleFactor = 0.81f // Adjust this value to zoom out more or less
    private val maxScaleFactor = 2f
    private val minScaleFactor = 0.81f

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    private val imageViewWidth get() = imageView.width
    private val imageViewHeight get() = imageView.height

    private val imageViewDrawableWidth get() = imageView.drawable.intrinsicWidth
    private val imageViewDrawableHeight get() = imageView.drawable.intrinsicHeight

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottiePin = binding.lottieAnimation1
        imageView = binding.imageView

        // Set up scale gesture detector
        scaleGestureDetector = ScaleGestureDetector(requireContext(), object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(minScaleFactor, maxScaleFactor)

                matrix.setScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                constrainMatrix()
                imageView.imageMatrix = matrix
                return true
            }
        })

        // Apply touch listener for zooming and scrolling
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)

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

                        matrix.postTranslate(deltaX, deltaY)
                        constrainMatrix()
                        imageView.imageMatrix = matrix

                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isDragging = false
                }
            }

            true
        }

        lottiePin.setOnClickListener {
            val intent = Intent(requireContext(), onCityPressed1::class.java)
            startActivity(intent)
        }
    }

    private fun constrainMatrix() {
        val values = FloatArray(9)
        matrix.getValues(values)
        val scaleX = values[Matrix.MSCALE_X]
        val scaleY = values[Matrix.MSCALE_Y]
        val transX = values[Matrix.MTRANS_X]
        val transY = values[Matrix.MTRANS_Y]

        val drawableWidth = imageView.drawable.intrinsicWidth
        val drawableHeight = imageView.drawable.intrinsicHeight

        val viewWidth = imageView.width
        val viewHeight = imageView.height

        val scaledDrawableWidth = drawableWidth * scaleX
        val scaledDrawableHeight = drawableHeight * scaleY

        // Calculate the maximum translation limits
        val maxTransX = maxOf(0f, scaledDrawableWidth - viewWidth)
        val maxTransY = maxOf(0f, scaledDrawableHeight - viewHeight)

        // Constrain translations to ensure the image doesn't move out of bounds
        val constrainedTransX = transX.coerceIn(-maxTransX, 0f)
        val constrainedTransY = transY.coerceIn(-maxTransY, 0f)

        matrix.setScale(scaleX, scaleY)
        matrix.postTranslate(constrainedTransX, constrainedTransY)
    }



}