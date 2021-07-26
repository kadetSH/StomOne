package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class ListOfApplicationsJS(
    @SerializedName("RequestNumber") val requestNumber : String,
    @SerializedName("DoctorFIO") val doctorFIO : String,
    @SerializedName("DoctorProfession") val doctorProfession : String,
    @SerializedName("DateRequest") val dateRequest : String,
    @SerializedName("TimeStart") val timeStart : String,
    @SerializedName("TimeEnd") val timeEnd : String
)
