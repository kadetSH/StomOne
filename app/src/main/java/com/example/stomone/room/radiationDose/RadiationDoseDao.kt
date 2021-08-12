package com.example.stomone.room.radiationDose

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RadiationDoseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRadiationDose(radiationDose: RRadiationDose)

    @Query("SELECT * FROM radiation_dose_table ORDER BY id ASC")
    fun readAllRadiationDose(): List<RRadiationDose>

    @Query("SELECT * FROM radiation_dose_table ORDER BY id ASC")
    fun readAllRadiationDoseLiveData(): LiveData<List<RRadiationDose>>

    @Query("DELETE FROM radiation_dose_table")
    fun deleteAllRadiationDose()
}