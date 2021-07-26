package com.example.stomone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contracts_table")
data class RContracts(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val contractNumber: String,
    val dateOfCreation: String,
    val finishedCheckBox: Int,
    val doctor: String
)
