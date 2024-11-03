package com.project.autoservicemobile.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.profile.models.CarUI
import com.squareup.picasso.Picasso

class CarouselRecyclerAdapter() :
    RecyclerView.Adapter<CarouselRecyclerAdapter.CarouselViewHolder>() {

    var items: List<String?> = listOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.requireViewById(R.id.carouselImage)

        fun bind(
            imageUrl: String?,
        ) {
            if (imageUrl != null)
                Picasso.get().load(imageUrl).error(R.drawable.news_image).into(image)
            else
                image.setImageResource(R.drawable.ic_trobber)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_carousel_item, parent, false)
        return CarouselViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(
            items[position]
        )
    }

    override fun getItemCount() = items.size
}