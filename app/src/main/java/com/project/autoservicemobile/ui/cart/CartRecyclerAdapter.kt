package com.project.autoservicemobile.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.cart.models.CartItemUI
import com.squareup.picasso.Picasso

class CartRecyclerAdapter (
    private val onDeleteClick: (CartItemUI) -> Unit) : RecyclerView
.Adapter<CartRecyclerAdapter.CartViewHolder>() {

    var items: List<CartItemUI> = listOf()
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
        private val deleteButton: Button = itemView.requireViewById(R.id.deleteBtn)
        private val imageView: ImageView = itemView.requireViewById(R.id.serviceImage)

        fun bind(item: CartItemUI, onDeleteClick: (CartItemUI) -> Unit){
            titleTextView.text = item.slot.service?.title ?: "Упс... Что-то пошло не так"
            priceTextView.text = item.slot.service?.priceText  ?: "Упс... Что-то пошло не так"


            deleteButton.setOnClickListener{
                onDeleteClick(item)
            }

            if (item.slot.service?.imageUrl  != null)
                Picasso.get().load(item.slot.service?.imageUrl).error(R.drawable.news_image).into(imageView)
            else
                imageView.setImageResource(R.drawable.news_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_cart_item, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position], onDeleteClick)
    }

    override fun getItemCount() = items.size
}