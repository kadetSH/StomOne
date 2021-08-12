package com.example.stomone.room.picturesVisit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PicturesVisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPicturesVisit(contract: RPicturesVisit)

    @Query("SELECT * FROM pictures_visit_table ORDER BY id ASC")
    fun readAllPicturesVisit(): List<RPicturesVisit>

    @Query("SELECT * FROM pictures_visit_table ORDER BY id ASC")
    fun readAllPicturesVisitLiveData(): LiveData<List<RPicturesVisit>>

    @Query("DELETE FROM pictures_visit_table")
    fun deleteAllPicturesVisit()
}