package com.project.autoservicemobile.ui.services

import android.app.Application
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.squareup.picasso.Picasso

class ServicesRecyclerAdapter (
    private val articles: List<ServiceUI>,
    private val onToCartClick: (ServiceUI) -> Unit,
    private val onFavoritesClick: (ServiceUI) -> Unit) : RecyclerView
.Adapter<ServicesRecyclerAdapter.ServicesViewHolder>() {

    class ServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
        private val imageView: ImageView = itemView.requireViewById(R.id.serviceImage)
        private var favoritesBtn: ImageView = itemView.requireViewById(R.id.favourites_btn)
        private var toCartButton: Button = itemView.requireViewById(R.id.toCartBtn)

        fun bind(item: ServiceUI, onToCartClick: (ServiceUI) -> Unit, onFavoritesClick: (ServiceUI) -> Unit){
            titleTextView.text = item.title
            priceTextView.text = item.priceText

            if(item.inCart){
                toCartButton.setText(R.string.from_cart)
            }else{
                toCartButton.setText(R.string.to_cart)
            }

            toCartButton.setOnClickListener{
                onCartBtnClick(item)
                onToCartClick(item)
            }

            favoritesBtn.setOnClickListener{onFavoritesClick(item)}

            Picasso.get().load(item.imageUrl).into(imageView)
        }

        private fun onCartBtnClick(service: ServiceUI){
            //val resId: TypedValue = TypedValue()

            if(service.inCart){
                toCartButton.setText(R.string.to_cart)
                //MAIN.applicationContext.theme.resolveAttribute(
                   // com.google.android.material.R.attr.colorOnSecondary,  resId, true )
                toCartButton.backgroundTintList = ContextCompat.getColorStateList(MAIN.applicationContext, R.color.blue_300)
            }
            else{
                toCartButton.setText(R.string.from_cart)
                //MAIN.applicationContext.theme.resolveAttribute(
                    //com.google.android.material.R.attr.colorPrimary,  resId, true )
                toCartButton.backgroundTintList = ContextCompat.getColorStateList(MAIN.applicationContext, R.color.gray_400)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_services_item, parent, false)
        return ServicesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.bind(articles[position], onToCartClick, onFavoritesClick)
    }

    override fun getItemCount() = articles.size
}