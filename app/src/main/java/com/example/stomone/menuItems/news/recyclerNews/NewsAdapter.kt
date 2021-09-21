package com.example.stomone.menuItems.news.recyclerNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R


class NewsAdapter(private val layoutInflater: LayoutInflater,
                  private val itemsArray: ArrayList<NewsItem>,
                  private val listener: ((newsItem: NewsItem, position: Int) -> Unit)?):  RecyclerView.Adapter<NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(layoutInflater.inflate(R.layout.template_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(itemsArray[position])

        holder.btn.setOnClickListener {
            listener?.invoke(itemsArray[position], position)
        }
    }

    override fun getItemCount(): Int = itemsArray.size
}