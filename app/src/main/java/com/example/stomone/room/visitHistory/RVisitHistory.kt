package com.example.stomone.room.visitHistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visit_history_table")
data class RVisitHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val service: String,
    val dateOfService: String,
    val type: String,
    val count: String,
    val sum: String,
    val doctor: String
)
