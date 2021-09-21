package com.example.stomone.room.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(news: RNews)

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun readAllNews(): List<RNews>

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun readAllNewsLiveData(): LiveData<List<RNews>>

    @Query("DELETE FROM news_table")
    fun deleteAllNews()
}