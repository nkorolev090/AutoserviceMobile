package com.project.autoservicemobile.ui.registrations.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.squareup.picasso.Picasso

class RegServicesRecyclerAdapter (
    private val items: List<SlotUI>,
    private val onWarrantyClick: (SlotUI) -> Unit,
    private val onFavoritesClick: (SlotUI) -> Unit) : RecyclerView
.Adapter<RegServicesRecyclerAdapter.RegServicesViewHolder>() {

    class RegServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
        private val mechanicTextView: TextView = itemView.requireViewById(R.id.mechanicText)
        private val imageView: ImageView = itemView.requireViewById(R.id.serviceImage)
        //private var favoritesBtn: ImageView = itemView.requireViewById(R.id.favourites_btn)
        private var warrantyButton: Button = itemView.requireViewById(R.id.warrantyBtn)

        private val toWarranty = "Узнать больше"

        fun bind(item: SlotUI, onToWarrantyClick: (SlotUI) -> Unit, onFavoritesClick: (SlotUI) -> Unit){
            titleTextView.text = item.service?.title
            priceTextView.text = item.service?.priceText
            mechanicTextView.text = item.mechanicNameText
            warrantyButton.text = toWarranty

            warrantyButton.setOnClickListener{
                onToWarrantyClick(item)
            }

            //favoritesBtn.setOnClickListener{onFavoritesClick(item)}

            if(item.service?.imageUrl != null) {
                Picasso.get().load(item.service?.imageUrl).into(imageView)
            }
            else{
                imageView.setImageResource(R.drawable.ic_trobber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegServicesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_registration_services_item, parent, false)
        return RegServicesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegServicesViewHolder, position: Int) {
        holder.bind(items[position], onWarrantyClick, onFavoritesClick)
    }

    override fun getItemCount() = items.size
}