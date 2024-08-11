package com.project.autoservicemobile.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter (private val articles: List<NewsArticleUI>, private val onClick: (NewsArticleUI) -> Unit) : RecyclerView
.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainTextView: TextView = itemView.findViewById(R.id.mainText)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionText)
        private val imageView: ImageView = itemView.findViewById(R.id.newsImage)
        private val loadMoreButton: MaterialButton = itemView.requireViewById(R.id.readMoreBtn)


        fun bind(item: NewsArticleUI, onClick: (NewsArticleUI) -> Unit){
            mainTextView.text = item.mainText
            descriptionTextView.text = item.descriptionText

            loadMoreButton.text = item.btnText
            loadMoreButton.setOnClickListener{onClick(item)}

            //imageView.setImageResource(R.drawable.news_image)
            Picasso.get().load(item.imageUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position], onClick)
    }

    override fun getItemCount() = articles.size
}