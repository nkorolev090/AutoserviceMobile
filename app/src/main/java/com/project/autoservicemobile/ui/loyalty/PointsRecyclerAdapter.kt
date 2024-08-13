package com.project.autoservicemobile.ui.loyalty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.loyalty.models.PointUI

class PointsRecyclerAdapter (
    private val items: List<PointUI>) : RecyclerView
.Adapter<PointsRecyclerAdapter.PointsViewHolder>() {

    class PointsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val descriptionTextView: TextView = itemView.requireViewById(R.id.descriptionText)
        private val countTextView: TextView = itemView.requireViewById(R.id.countText)

        fun bind(item: PointUI){
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            countTextView.text = item.countText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_points_item, parent, false)
        return PointsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}