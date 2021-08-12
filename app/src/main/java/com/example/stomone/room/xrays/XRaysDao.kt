package com.example.stomone.room.xrays

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface XRaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRXrays(contract: RXRays)

    @Query("SELECT * FROM x_rays_table ORDER BY id ASC")
    fun readAllXRays(): List<RXRays>

    @Query("SELECT * FROM x_rays_table ORDER BY id ASC")
    fun readAllRXRaysLiveData(): LiveData<List<RXRays>>

    @Query("DELETE FROM x_rays_table")
    fun deleteAllRXRays()
}