package com.le.aestroider.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import com.le.aestroider.feature.home.viewholder.HomeViewHolder

/**
 * <p>Adapter that populates the Neo (Near Earth Object) feeds.</p>
 * <p>It exposes two livedata observers:</p>
 * <p>1- onClickObserver: It is used to observe clicks on the list item.</p>
 * <p>2- loadNextPageObserver: It is used to observe when the list has scrolled to the end,
 * so that new items can be retrieved and appended to the list, if required for
 * infinite scrolling.</p>
 *
 * @author Usman
 */
class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

    var listItems = mutableListOf<NearEarthObject>()
    val onClickObserver = MutableLiveData<NearEarthObject>()
    val loadNextPageObserver = MutableLiveData<Unit>()

    // Methods such as add* can be moved to a base class if project expands, so that others adapters can
    // * use them without writing their own.
    fun add(item: NearEarthObject) {
        listItems.add(item)
        notifyItemInserted(listItems.size - 1)
    }

    fun addAll(items: MutableList<NearEarthObject>) {
        for (item in items) {
            add(item)
        }
    }

    fun clearAll() {
        listItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.neo_row, parent, false), onClickObserver)
    }

    override fun getItemCount(): Int {
        return listItems.size

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.setData(listItems.get(position))
        if (position == listItems.size - 1) {
            // information such as page number or list size can be provided to the listeners of this observer
            loadNextPageObserver.value = Unit
        }
    }
}
