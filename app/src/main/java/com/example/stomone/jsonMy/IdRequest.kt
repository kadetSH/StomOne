package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class IdRequest(
    @SerializedName("Result") val result : String,
    @SerializedName("ID") val id : String
)
