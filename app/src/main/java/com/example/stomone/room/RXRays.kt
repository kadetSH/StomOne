package com.example.stomone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "x_rays_table")
data class RXRays(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val datePhoto: String,
    val numberDirection: String,
    val typeOfResearch: String,
    val financing: String,
    val teeth: String,
    val doctor: String
)
