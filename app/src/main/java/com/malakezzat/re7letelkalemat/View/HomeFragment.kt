package com.malakezzat.re7letelkalemat.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var lottiePin1: LottieAnimationView
    private lateinit var lottiePin2: LottieAnimationView
    private lateinit var lottiePin3: LottieAnimationView
    private lateinit var lottiePin4: LottieAnimationView
    private lateinit var lottiePin5: LottieAnimationView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageView: ImageView
    private  var  isFirstHomeRun : Boolean = true
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private val matrix: Matrix by lazy {
        Matrix().apply {
            postTranslate(-1300f, -600f)
        }
    }
    private var scaleFactor = 0.81f // Adjust this value to zoom out more or less
    private val maxScaleFactor = 2f
    private val minScaleFactor = 0.81f

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    private val pin1X = 235f // X coordinate of the first pin relative to the image in dp
    private val pin1Y = 210f // Y coordinate of the first pin relative to the image in dp

    private val pin2X = 575f // X coordinate of the second pin relative to the image in dp
    private val pin2Y = 700f // Y coordinate of the second pin relative to the image in dp

    private val pin3X = 740f // X coordinate of the second pin relative to the image in dp
    private val pin3Y = 180f // Y coordinate of the second pin relative to the image in dp

    private val pin4X = 565f // X coordinate of the second pin relative to the image in dp
    private val pin4Y = 220f // Y coordinate of the second pin relative to the image in dp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        isFirstHomeRun = sharedPreferences.getBoolean("isFirstHomeRun", true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottiePin1 = binding.lottieAnimation1
        lottiePin2 = binding.lottieAnimation2
        lottiePin3 = binding.lottieAnimation3
        lottiePin4 = binding.lottieAnimation4
        lottiePin5 = binding.lottieAnimation5
        imageView = binding.imageView

        constrainMatrix()
        imageView.imageMatrix = matrix

        imageView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                imageView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateLottiePinPositions()
                if (isFirstHomeRun)
                showFragmentTapTarget()
            }
        })

        // Set up scale gesture detector
        scaleGestureDetector = ScaleGestureDetector(requireContext(), object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(minScaleFactor, maxScaleFactor)

                matrix.setScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                constrainMatrix()
                imageView.imageMatrix = matrix
                updateLottiePinPositions()
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
                        updateLottiePinPositions()
                        Log.i("homeTest", "onViewCreated: "
                        + "deltaX: " + deltaX +
                                "deltaY: " + deltaY)

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

//        lottiePin1.setOnClickListener {
//            val intent = Intent(requireContext(), onCityPressed1::class.java)
//            startActivity(intent)
//        }

        lottiePin2.setOnClickListener {
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

    private fun updateLottiePinPositions() {
        val values = FloatArray(9)
        matrix.getValues(values)
        val scaleX = values[Matrix.MSCALE_X]
        val scaleY = values[Matrix.MSCALE_Y]
        val transX = values[Matrix.MTRANS_X]
        val transY = values[Matrix.MTRANS_Y]

        // Convert pin coordinates from dp to pixels
        val pin1XInPx = dpToPx(pin1X)
        val pin1YInPx = dpToPx(pin1Y)
        val pin2XInPx = dpToPx(pin2X)
        val pin2YInPx = dpToPx(pin2Y)
        val pin3XInPx = dpToPx(pin3X)
        val pin3YInPx = dpToPx(pin3Y)
        val pin4XInPx = dpToPx(pin4X)
        val pin4YInPx = dpToPx(pin4Y)

        // Calculate the transformed position of the pins
        val transformedX1 = (pin1XInPx * scaleX) + transX
        val transformedY1 = (pin1YInPx * scaleY) + transY
        val transformedX2 = (pin2XInPx * scaleX) + transX
        val transformedY2 = (pin2YInPx * scaleY) + transY
        val transformedX3 = (pin3XInPx * scaleX) + transX
        val transformedY3 = (pin3YInPx * scaleY) + transY
        val transformedX4 = (pin4XInPx * scaleX) + transX
        val transformedY4 = (pin4YInPx * scaleY) + transY

        // Update the positions of the LottieAnimationViews
        lottiePin1.x = transformedX1 - (lottiePin1.width / 2)
        lottiePin1.y = transformedY1 - (lottiePin1.height / 2)

        lottiePin2.x = transformedX2 - (lottiePin2.width / 2)
        lottiePin2.y = transformedY2 - (lottiePin2.height / 2)

        lottiePin3.x = transformedX3 - (lottiePin3.width / 2)
        lottiePin3.y = transformedY3 - (lottiePin3.height / 2)

        lottiePin4.x = transformedX4 - (lottiePin4.width / 2)
        lottiePin4.y = transformedY4 - (lottiePin4.height / 2)
    }

    private fun dpToPx(dp: Float): Float {
        return dp * requireContext().resources.displayMetrics.density
    }

    private fun showFragmentTapTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(lottiePin5,
            getString(R.string.welcome_to_home_fragment),
            getString(R.string.home_fragment_disc)
        ).targetRadius(60)
            .outerCircleColor(R.color.white)
            .outerCircleAlpha(1f)
            .titleTextSize(26)
            .descriptionTextSize(20)
            .textColor(R.color.my_primary_variant_color)
            .drawShadow(true)
            .cancelable(true)
            .tintTarget(true)
            .transparentTarget(true),
            object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                }

                override fun onTargetDismissed(view: TapTargetView?, userInitiated: Boolean) {
                    super.onTargetDismissed(view, userInitiated)
                    showLottiePinTapTarget()
                }
            }
        )
        sharedPreferences.edit().putBoolean("isFirstHomeRun", false).apply()
    }
    private fun showLottiePinTapTarget() {
        TapTargetView.showFor(requireActivity(), TapTarget.forView(lottiePin2,
            getString(R.string.click_here),
            getString(R.string.beginning_journey_in_saudi_arabia)
        ).targetRadius(35)
            .outerCircleColor(R.color.white)
            .outerCircleAlpha(1f)
            .titleTextSize(26)
            .descriptionTextSize(20)
            .textColor(R.color.my_primary_variant_color)
            .drawShadow(true)
            .cancelable(true)
            .tintTarget(true)
            .transparentTarget(true)
        )
        sharedPreferences.edit().putBoolean("isFirstHomeRun", false).apply()
    }
}
