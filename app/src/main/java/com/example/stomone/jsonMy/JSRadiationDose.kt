package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSRadiationDose(
    @SerializedName("Date") val date : String,
    @SerializedName("Teeth") val teeth : String,
    @SerializedName("TypeOfResearch") val typeOfResearch : String,
    @SerializedName("RadiationDose") val radiationDose : String,
    @SerializedName("Doctor") val doctor : String
)
