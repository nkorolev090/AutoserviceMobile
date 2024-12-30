package com.project.autoservicemobile.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.cart.models.CartRecycleItem
import com.squareup.picasso.Picasso

class CartRecyclerAdapter(
    private val onDeleteClick: (CartRecycleItem.CartItemUI) -> Unit
) : RecyclerView
.Adapter<CartRecyclerAdapter.CartViewHolder>() {

    private companion object {
        const val VIEW_TYPE_TITLE = 1
        const val VIEW_TYPE_CART_ITEM = 2
    }

    var items: List<CartRecycleItem> = listOf()

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CartRecycleItem, onDeleteClick: (CartRecycleItem.CartItemUI) -> Unit) {
            when(item){
                is CartRecycleItem.CartItemUI -> {
                    val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
                    val priceTextView: TextView = itemView.requireViewById(R.id.priceText)
                    val timeTextView: TextView = itemView.requireViewById(R.id.timeText)
                    val mechanicTextView: TextView = itemView.requireViewById(R.id.mechanicText)
                    val deleteButton: Button = itemView.requireViewById(R.id.deleteBtn)
                    val imageView: ImageView = itemView.requireViewById(R.id.serviceImage)

                    titleTextView.text = item.slot.service?.title ?: "Упс... Что-то пошло не так"
                    priceTextView.text = item.slot.service?.priceText ?: "Упс... Что-то пошло не так"
                    val dateTimeText = "${item.slot.startDateText} ${item.slot.startTimeText}"
                    timeTextView.text = dateTimeText
                    mechanicTextView.text = item.slot.mechanicNameText
                    deleteButton.setOnClickListener {
                        onDeleteClick(item)
                    }

                    if (item.slot.service?.imageUrl != null)
                        Picasso.get().load(item.slot.service?.imageUrl).error(R.drawable.news_image)
                            .into(imageView)
                    else
                        imageView.setImageResource(R.drawable.news_image)
                }
                is CartRecycleItem.Title -> {
                    val titleTextView: TextView = itemView.requireViewById(R.id.titleText)
                    titleTextView.setText(R.string.anavailable_title)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = if (viewType == VIEW_TYPE_CART_ITEM)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.li_cart_item, parent, false)
        else
            LayoutInflater.from(parent.context)
                .inflate(R.layout.li_cart_title, parent, false)
        return CartViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CartRecycleItem.CartItemUI -> VIEW_TYPE_CART_ITEM
            is CartRecycleItem.Title -> VIEW_TYPE_TITLE
        }
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position], onDeleteClick)
    }

    override fun getItemCount() = items.size
}