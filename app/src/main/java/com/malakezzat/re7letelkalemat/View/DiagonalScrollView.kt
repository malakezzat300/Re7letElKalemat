package com.malakezzat.re7letelkalemat.View

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import kotlin.math.abs

class DiagonalScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

    private var horizontalScrollView: HorizontalScrollView? = null
    private var lastX = 0f
    private var lastY = 0f

    // Scaling factor to slow down the scrolling speed
    private val scrollFactor = 0.5f

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0 && getChildAt(0) is HorizontalScrollView) {
            horizontalScrollView = getChildAt(0) as HorizontalScrollView
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val currentX = ev.x
        val currentY = ev.y

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = currentX
                lastY = currentY
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = (currentX - lastX) * scrollFactor
                val deltaY = (currentY - lastY) * scrollFactor

                val isHorizontalSwipe = abs(deltaX) > abs(deltaY)
                return if (isHorizontalSwipe) {
                    horizontalScrollView?.onInterceptTouchEvent(ev) ?: false
                } else {
                    super.onInterceptTouchEvent(ev)
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        horizontalScrollView?.onTouchEvent(ev)

        val currentX = ev.x
        val currentY = ev.y

        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val deltaX = (ev.x - lastX) * scrollFactor
                val deltaY = (ev.y - lastY) * scrollFactor

                // Scroll diagonally with slower speed
                scrollBy(-deltaX.toInt(), -deltaY.toInt())
                horizontalScrollView?.scrollBy(-deltaX.toInt(), 0)

                lastX = currentX
                lastY = currentY
            }
        }

        return super.onTouchEvent(ev)
    }
}
