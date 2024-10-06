package com.project.autoservicemobile.ui.services.slots

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.squareup.picasso.Picasso
import java.lang.NullPointerException
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

class SlotsRecyclerAdapter(
    private val onItemClick: (SlotUI) -> Unit
) :
    RecyclerView.Adapter<SlotsRecyclerAdapter.ServicesViewHolder>() {

    var items: List<SlotUI> = listOf()
    private var selectedPosition = -1

    // This property is only valid between onCreateView and
    // onDestroyView.
    class ServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val dateTextView: TextView = itemView.requireViewById(R.id.dateText)
        private val timeTextView: TextView = itemView.requireViewById(R.id.timeText)
        private val mechanicNameTextView: TextView = itemView.requireViewById(R.id.mechanicName)

        fun bind(
            item: SlotUI,
            position: Int,
            selected: Boolean,
            onItemClick: (SlotUI) -> Unit,
            setItemSelection: (Int) -> Unit
        ) {
            dateTextView.text = item.startDateText
            timeTextView.text = item.startTimeText
            mechanicNameTextView.text = item.mechanicNameText

            setSelected(selected)
            itemView.setOnClickListener {
                onItemClick(item)
                setItemSelection(position)
            }
        }

        private fun setSelected(selected: Boolean) {
            if (selected) {
                itemView.setBackgroundResource(R.drawable.secondary_bg_with_stroke)
            } else {
                itemView.setBackgroundResource(R.drawable.secondary_bg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_slots_item, parent, false)
        return ServicesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.bind(
            items[position],
            position,
            selectedPosition == position,
            onItemClick
        ) { pos -> setItemSelection(pos) }
    }

    private fun setItemSelection(position: Int) {
        if (position == RecyclerView.NO_POSITION) return

        notifyItemChanged(selectedPosition)

        if (selectedPosition != RecyclerView.NO_POSITION) items[selectedPosition].selected = false

        if(selectedPosition != position){
            selectedPosition = position
            notifyItemChanged(selectedPosition)
            items[selectedPosition].selected = true
        }
        else{
            selectedPosition = -1
        }
    }

    override fun getItemCount() = items.size
}