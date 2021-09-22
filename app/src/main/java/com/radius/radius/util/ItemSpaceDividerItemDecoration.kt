package com.radius.radius.util

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class ItemSpaceDividerItemDecoration(var shouldAddSpaceToFirstItem: Boolean = true, var shouldAddSpaceToLastItem: Boolean = true, var spacing: Int) : RecyclerView.ItemDecoration() {
    enum class LayoutType {
        GRID,
        HORIZONTAL,
        VERTICAL,
        NONE
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): LayoutType {
        if (layoutManager == null) {
            return LayoutType.NONE
        }
        if (layoutManager is GridLayoutManager) return LayoutType.GRID
        return if (layoutManager?.canScrollHorizontally()) LayoutType.HORIZONTAL else LayoutType.VERTICAL
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(outRect: Rect, layoutManager: RecyclerView.LayoutManager?, position: Int, itemCount: Int) {
        val type = resolveDisplayMode(layoutManager)
        when (type) {
            LayoutType.HORIZONTAL -> {
                outRect.left = if (position == 0) (if (shouldAddSpaceToFirstItem) spacing else 0) else 0
                outRect.right = if (position == itemCount - 1) (if (shouldAddSpaceToLastItem) spacing else 0) else spacing
                outRect.top = 0
                outRect.bottom = 0
            }

            LayoutType.VERTICAL -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = if (position == 0) (if (shouldAddSpaceToFirstItem) spacing else 0) else 0
                outRect.bottom = if (position == itemCount - 1) (if (shouldAddSpaceToLastItem) spacing else 0) else spacing
            }

            else -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = 0
                outRect.bottom = 0
            }
        }
    }

}
