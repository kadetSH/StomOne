package com.example.stomone.menuItems.news.recyclerNews

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class NewsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val titleEdit: TextView = itemView.findViewById<TextView>(R.id.template_news_title)
    private val contentEdit: TextView = itemView.findViewById<TextView>(R.id.template_news_content)
    val btn: Button = itemView.findViewById<Button>(R.id.template_news_btn)

    fun bind(item: NewsItem){
        titleEdit.text = item.title
        contentEdit.text = item.content
    }

}