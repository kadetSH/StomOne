package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSVisitHistory(
    @SerializedName("Service") val service : String,
    @SerializedName("DateOfService") val dateOfService : String,
    @SerializedName("Type") val type : String,
    @SerializedName("Count") val count : String,
    @SerializedName("Sum") val sum : String,
    @SerializedName("Doctor") val doctor : String
)
