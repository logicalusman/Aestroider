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
 * <p>Adapter that populates the neo feeds.</p>
 * Reservation: Methods such as add* can moved to a base class, so that others adapters can
 * use them without writing their own.
 */
class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

    var listItems: MutableList<NearEarthObject>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }
    val onClickObserver = MutableLiveData<NearEarthObject>()

    fun add(item: NearEarthObject) {
        listItems?.let {
            it.add(item)
            notifyItemInserted(it.size - 1)
        }
    }

    fun addAll(items: MutableList<NearEarthObject>) {
        for (item in items) {
            add(item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.neo_row, parent, false), onClickObserver)
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.setData(listItems?.get(position))
    }
}
