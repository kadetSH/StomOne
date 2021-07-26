package com.example.stomone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_info_table")
data class RContactInformation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val surname: String,
    val name: String,
    val patronymic: String,
    val birth: String,
    val gender: String,
    val telephone: String,
    val address: String
)
