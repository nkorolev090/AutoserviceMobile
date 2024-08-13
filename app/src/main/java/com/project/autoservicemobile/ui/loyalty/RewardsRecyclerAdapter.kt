package com.project.autoservicemobile.ui.loyalty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.loyalty.models.RewardUI
import com.squareup.picasso.Picasso

class RewardsRecyclerAdapter (
    private val items: List<RewardUI>) : RecyclerView
.Adapter<RewardsRecyclerAdapter.RewardsViewHolder>() {

    class RewardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val descriptionTextView: TextView = itemView.requireViewById(R.id.descriptionText)
        private val imageView: ImageView = itemView.requireViewById(R.id.rewardImage)

        fun bind(item: RewardUI){
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            Picasso.get().load(item.imageUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_rewards_item, parent, false)
        return RewardsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RewardsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}