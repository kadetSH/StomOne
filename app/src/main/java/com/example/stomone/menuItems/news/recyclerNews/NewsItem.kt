package com.example.stomone.menuItems.news.recyclerNews

import java.io.Serializable

data class NewsItem(
    var title: String,
    var content: String,
    var imagePath: String
) : Serializable
