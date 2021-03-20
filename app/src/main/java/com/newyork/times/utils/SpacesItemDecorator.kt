

package com.newyork.times.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecorator(space: Int) : RecyclerView.ItemDecoration() {

    private val space: Int = space.dp

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val orientation = (parent.layoutManager as? LinearLayoutManager)?.orientation
            ?: LinearLayoutManager.VERTICAL
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            when (parent.getChildLayoutPosition(view)) {
                0 -> {
                    outRect.left = space
                    outRect.right = space
                }
                else -> outRect.right = space
            }
        } else {
            when (parent.getChildLayoutPosition(view)) {
                0 -> {
                    outRect.top = space
                    outRect.left = space
                    outRect.right = space
                    outRect.bottom = space
                }
                else -> {
                    outRect.left = space
                    outRect.right = space
                    outRect.bottom = space
                }
            }
        }
    }
}

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
