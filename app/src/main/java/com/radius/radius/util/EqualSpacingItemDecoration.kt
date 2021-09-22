package com.radius.radius.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class EqualSpacingItemDecoration @JvmOverloads constructor(
    private val spacing: Int,
    private var displayMode: Int = -1,
    private val shouldIgnoreFirstTopMargin: Boolean = false
) :
    ItemDecoration() {
    private var isIgnore = false

    constructor(spacing: Int, shouldIngoreFirstTopMargin: Boolean) : this(
        spacing,
        -1,
        shouldIngoreFirstTopMargin
    ) {
    }

    fun isIgnoreFirstItem(isIgnore: Boolean) {
        this.isIgnore = isIgnore
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager)
        }
        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = if (position == 0 && isIgnore) 0 else spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = 0
                outRect.bottom = 0
            }
            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = 0
                outRect.bottom = 0
            }
            GRID -> if (layoutManager is GridLayoutManager) {
                val totalColumns = layoutManager.spanCount
                val totalRows = itemCount / totalColumns
                val lastColumn = totalColumns - 1
                val lastRow = totalRows - 1
                val currentRow = position / totalColumns
                val currentColumn = position % totalColumns
                if (currentColumn == 0) spacing else spacing / 2
                outRect.right = if (currentColumn == lastColumn) spacing else spacing / 2
                outRect.top = if (shouldIgnoreFirstTopMargin) 0 else spacing
                outRect.bottom = if (currentRow == lastRow) spacing else 0
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        return if (layoutManager!!.canScrollHorizontally()) HORIZONTAL else VERTICAL
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
    }
}
