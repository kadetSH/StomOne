package com.example.stomone.room.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class RNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String,
    val imagePath: String
)
