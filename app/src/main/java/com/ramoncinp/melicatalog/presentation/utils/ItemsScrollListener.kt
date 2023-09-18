package com.ramoncinp.melicatalog.presentation.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
    private val onScrolled: (visibleItemCount: Int, firstVisibleItemPosition: Int) -> Unit
): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = linearLayoutManager.childCount
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        onScrolled(visibleItemCount, firstVisibleItemPosition)
    }
}
