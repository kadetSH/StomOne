package com.example.stomone.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_table")
data class RLogin(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val surname : String,
    val name : String,
    val patronymic : String,
    val password : String
)
