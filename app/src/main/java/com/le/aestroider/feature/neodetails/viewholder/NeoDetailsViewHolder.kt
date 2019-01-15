package com.le.aestroider.feature.neodetails.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.le.aestroider.domain.NearEarthObjectDetailsItem
import kotlinx.android.synthetic.main.neo_details_row.view.*

class NeoDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(data: NearEarthObjectDetailsItem?) {
        data?.let {
            itemView.icon_iv.setImageResource(it.icon)
            if( it.isHazardousItem ){
                it.hazardousItemHeaderText?.let { it1 -> itemView.header_tv.setText(it1) }
            } else {
                itemView.header_tv.text = it.headerText
            }
            itemView.sub_tv.setText(it.subText)
        }
    }
}