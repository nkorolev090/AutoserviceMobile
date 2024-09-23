package com.project.autoservicemobile.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.home.models.NewsArticleUI
import com.squareup.picasso.Picasso

class NewsRecyclerAdapter (private val onClick: (NewsArticleUI) -> Unit) : RecyclerView
.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    var items: List<NewsArticleUI> = listOf()
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainTextView: TextView = itemView.requireViewById(R.id.mainText)
        private val descriptionTextView: TextView = itemView.requireViewById(R.id.descriptionText)
        private val imageView: ImageView = itemView.requireViewById(R.id.newsImage)
        private val loadMoreButton: MaterialButton = itemView.requireViewById(R.id.readMoreBtn)


        fun bind(item: NewsArticleUI, onClick: (NewsArticleUI) -> Unit){
            mainTextView.text = item.titleText
            descriptionTextView.text = item.sourceText

            loadMoreButton.setText(R.string.load_more_btn_text)
            loadMoreButton.isEnabled = item.btnEnabled
            loadMoreButton.setOnClickListener{onClick(item)}

            //imageView.setImageResource(R.drawable.news_image)
            if(item.urlToImage != null)
                Picasso.get().load(item.urlToImage).error(R.drawable.news_image).into(imageView)
            else
                imageView.setImageResource(R.drawable.news_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount() = items.size
}