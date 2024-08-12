package com.project.autoservicemobile.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.services.models.ServiceUI

class CartRecyclerAdapter (
    private val items: List<ServiceUI>,
    private val onDeleteClick: (ServiceUI) -> Unit) : RecyclerView
.Adapter<CartRecyclerAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
        private var deleteButton: ImageView = itemView.requireViewById(R.id.deleteBtn)

        fun bind(item: ServiceUI, onToCartClick: (ServiceUI) -> Unit){
            titleTextView.text = item.title
            priceTextView.text = item.priceText


            deleteButton.setOnClickListener{
                onToCartClick(item)
            }
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