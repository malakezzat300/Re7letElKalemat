package com.malakezzat.re7letelkalemat.View

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val left: Int,private val right: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 40
        outRect.bottom = 40
        if (parent.getChildAdapterPosition(view)%2==0) {
            outRect.left = left
            outRect.right = left/3+10

        }
        if (parent.getChildAdapterPosition(view)%2==1) {
            outRect.right = right
            outRect.left = right/3+10

        }

    }
}