package com.example.stomone.room.radiationDose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "radiation_dose_table")
data class RRadiationDose(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val date : String,
    val teeth : String,
    val typeOfResearch : String,
    val radiationDose : String,
    val doctor : String
)
