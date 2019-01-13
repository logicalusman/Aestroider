package com.le.aestroider.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObject
import kotlinx.android.synthetic.main.neo_row.view.*

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeViewHolder>() {

    var listItems: List<NearEarthObject>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }
    val onClickObserver = MutableLiveData<NearEarthObject>()

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


class HomeViewHolder(itemView: View, onClickObserver: MutableLiveData<NearEarthObject>) :
    RecyclerView.ViewHolder(itemView) {

    private var data: NearEarthObject? = null

    init {
        itemView.setOnClickListener { _ ->
            data?.let {
                onClickObserver.value = it
            }
        }
    }

    fun setData(neoData: NearEarthObject?) {
        neoData?.let {
            this.data = neoData
            itemView.neo_name_tv.text = it.name
            itemView.neo_date_tv.text = it.closeApproachDate
            if (it.isPotentialyHazerdous) {
                itemView.potentialHazardWarning_iv.visibility = View.VISIBLE
            } else {
                itemView.potentialHazardWarning_iv.visibility = View.GONE
            }
        }
    }

}