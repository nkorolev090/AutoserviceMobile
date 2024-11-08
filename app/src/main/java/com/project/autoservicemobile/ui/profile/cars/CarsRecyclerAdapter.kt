package com.project.autoservicemobile.ui.profile.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.profile.models.CarUI

class CarsRecyclerAdapter(
    private val onItemClick: (CarUI) -> Unit
) :
    RecyclerView.Adapter<CarsRecyclerAdapter.ServicesViewHolder>() {

    var items: List<CarUI> = listOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    class ServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val brandTextView: TextView = itemView.requireViewById(R.id.defaultCarBrandText)
        private val modelTextView: TextView = itemView.requireViewById(R.id.defaultCarModelText)
        private val numberTextView: TextView = itemView.requireViewById(R.id.numberText)

        fun bind(
            item: CarUI,
            onItemClick: (CarUI) -> Unit,
        ) {
            brandTextView.text = item.brand
            modelTextView.text = item.model
            numberTextView.text = item.number

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_cars_item, parent, false)
        return ServicesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.bind(
            items[position],
            onItemClick
        )
    }

    override fun getItemCount() = items.size
}