package com.le.aestroider.feature.neodetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.le.aestroider.R
import com.le.aestroider.domain.NearEarthObjectDetailsItem
import com.le.aestroider.feature.neodetails.viewholder.NeoDetailsViewHolder

class NeoDetailsAdapter(private val context: Context) : RecyclerView.Adapter<NeoDetailsViewHolder>() {

    var listItems: List<NearEarthObjectDetailsItem>? = null
        set(list) {
            field = list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeoDetailsViewHolder {
        return NeoDetailsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.neo_details_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: NeoDetailsViewHolder, position: Int) {
        holder.setData(listItems?.get(position))
    }
}


