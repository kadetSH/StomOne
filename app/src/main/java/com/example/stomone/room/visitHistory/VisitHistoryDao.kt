package com.example.stomone.room.visitHistory

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VisitHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVisitHistory(contract: RVisitHistory)

    @Query("SELECT * FROM visit_history_table ORDER BY id ASC")
    fun readAllVisitHistory(): List<RVisitHistory>

    @Query("SELECT * FROM visit_history_table ORDER BY id ASC")
    fun readAllVisitHistoryLiveData(): LiveData<List<RVisitHistory>>

    @Query("DELETE FROM visit_history_table")
    fun deleteAllVisitHistory()
}