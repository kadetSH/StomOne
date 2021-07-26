package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class BusinessHoursResultJS(
    @SerializedName("Time") val time : String,
    @SerializedName("Access") val access : Int
)
