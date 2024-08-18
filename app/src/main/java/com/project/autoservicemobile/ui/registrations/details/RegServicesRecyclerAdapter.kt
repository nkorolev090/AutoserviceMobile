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
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.squareup.picasso.Picasso

class RegServicesRecyclerAdapter (
    private val items: List<ServiceUI>,
    private val onWarrantyClick: (ServiceUI) -> Unit,
    private val onFavoritesClick: (ServiceUI) -> Unit) : RecyclerView
.Adapter<RegServicesRecyclerAdapter.RegServicesViewHolder>() {

    class RegServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
        //private val mechanicTextView: TextView = itemView.requireViewById(R.id.priceText)
        private val imageView: ImageView = itemView.requireViewById(R.id.serviceImage)
        private var favoritesBtn: ImageView = itemView.requireViewById(R.id.favourites_btn)
        private var warrantyButton: Button = itemView.requireViewById(R.id.warrantyBtn)

        private val toWarranty = "Гарантийное обращение"
        private val fromWarranty = "Обращение отправлено"

        fun bind(item: ServiceUI, onToWarrantyClick: (ServiceUI) -> Unit, onFavoritesClick: (ServiceUI) -> Unit){
            titleTextView.text = item.title
            priceTextView.text = item.priceText
            //mechanicTextView.text = item.mechanicName

            if(item.inWarranty){
                warrantyButton.text = fromWarranty
            }else{
                warrantyButton.text = toWarranty
            }

            warrantyButton.setOnClickListener{
                onWarrantyBtnClick(item)
                onToWarrantyClick(item)
            }

            favoritesBtn.setOnClickListener{onFavoritesClick(item)}

            Picasso.get().load(item.imageUrl).into(imageView)
        }

        private fun onWarrantyBtnClick(service: ServiceUI){
            //val resId: TypedValue = TypedValue()

            if(service.inWarranty){
                warrantyButton.text = toWarranty
                //MAIN.applicationContext.theme.resolveAttribute(
                // com.google.android.material.R.attr.colorOnSecondary,  resId, true )
                warrantyButton.backgroundTintList = ContextCompat.getColorStateList(MAIN.applicationContext, R.color.blue_300)
            }
            else{
                warrantyButton.text = fromWarranty
                //MAIN.applicationContext.theme.resolveAttribute(
                //com.google.android.material.R.attr.colorPrimary,  resId, true )
                warrantyButton.backgroundTintList = ContextCompat.getColorStateList(MAIN.applicationContext, R.color.gray_400)
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