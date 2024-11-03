package com.project.autoservicemobile.ui.registrations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CarouselRecyclerAdapter
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI

class RegistrationsRecyclerAdapter (private val onClick: (RegistrationUI) -> Unit) : RecyclerView
.Adapter<RegistrationsRecyclerAdapter.RegistrationsViewHolder>() {

    var items: List<RegistrationUI> = listOf()
    class RegistrationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val numberTextView: TextView = itemView.requireViewById(R.id.numberText)
        private val statusTextView: TextView = itemView.requireViewById(R.id.statusText)
        private val carousel: RecyclerView = itemView.requireViewById(R.id.servicesRecycler)

        private val viewMoreText = "Узнать больше"
        fun bind(item: RegistrationUI, onClick: (RegistrationUI) -> Unit){
            mainTextView.text = item.registrationTitle
            numberTextView.text = item.registrationNumber
            statusTextView.text = item.statusTitle
            statusTextView.setTextColor(item.status.toStatusColor(itemView.context))

            carousel.layoutManager = LinearLayoutManager(itemView.context)
            (carousel.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
            carousel.adapter = CarouselRecyclerAdapter()

            val imageUrls = item.slots?.map { it.service?.imageUrl }
            if(imageUrls != null)
                (carousel.adapter as CarouselRecyclerAdapter).items = imageUrls

            itemView.setOnClickListener{onClick(item)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_registrations_item, parent, false)
        return RegistrationsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegistrationsViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount() = items.size
}

private fun RegStatusEnum.toStatusColor(context : Context): Int =
    when (this) {
        RegStatusEnum.PROCESSING -> context.getColor(R.color.yellow_gray_300)
        RegStatusEnum.APPROVED -> context.getColor(R.color.green_gray_300)
        RegStatusEnum.ABORTED -> context.getColor(R.color.red_gray_300)
        RegStatusEnum.COMPLETED -> context.getColor(R.color.teal_gray_200)
        RegStatusEnum.WARRANTY_WORK -> context.getColor(R.color.orange_gray_300)
    }