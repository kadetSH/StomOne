package com.example.stomone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures_visit_table")
data class RPicturesVisit(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val dateOfReceipt : String,
    val numberPicture : String,
    val doctor : String
)
