package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSXRays(
    @SerializedName("DatePhoto") val datePhoto : String,
    @SerializedName("NumberDirection") val numberDirection : String,
    @SerializedName("TypeOfResearch") val typeOfResearch : String,
    @SerializedName("Financing") val financing : String,
    @SerializedName("Teeth") val teeth : String,
    @SerializedName("Doctor") val doctor : String
)
