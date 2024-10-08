package com.project.autoservicemobile.ui.registrations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.squareup.picasso.Picasso

class RegistrationsRecyclerAdapter (private val onClick: (RegistrationUI) -> Unit) : RecyclerView
.Adapter<RegistrationsRecyclerAdapter.RegistrationsViewHolder>() {

    var items: List<RegistrationUI> = listOf()
    class RegistrationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainTextView: TextView = itemView.requireViewById(R.id.titleText)
        private val completedDateTextView: TextView = itemView.requireViewById(R.id.completeOnText)
        private val imageView: ImageView = itemView.requireViewById(R.id.registrationImage)
        private val viewMoreButton: MaterialButton = itemView.requireViewById(R.id.viewMoreBtn)

        private val viewMoreText = "Узнать больше"
        fun bind(item: RegistrationUI, onClick: (RegistrationUI) -> Unit){
            mainTextView.text = item.registrationTitle
            completedDateTextView.text = item.statusTitle

            viewMoreButton.text = viewMoreText
            viewMoreButton.setOnClickListener{onClick(item)}

            Picasso.get().load(item.imageURL).into(imageView)
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