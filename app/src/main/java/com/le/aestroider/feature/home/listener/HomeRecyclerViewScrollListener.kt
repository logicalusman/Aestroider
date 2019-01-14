package com.le.aestroider.feature.home.listener

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Caller can observe loadMoreDataObserver in order to get notification when the list has
 * scrolled to end and more data needs to be loaded.
 *
 * @author Usman
 *
 */
class HomeRecyclerViewScrollListener(val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    val loadMoreDataObserver = MutableLiveData<Unit>()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            loadMoreDataObserver.value = Unit
        }
    }
}