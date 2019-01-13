package com.le.aestroider.feature.home.viewholder

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.le.aestroider.domain.NearEarthObject
import kotlinx.android.synthetic.main.neo_row.view.*


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