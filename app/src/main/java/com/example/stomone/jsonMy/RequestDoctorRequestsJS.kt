package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class RequestDoctorRequestsJS(
    @SerializedName("Doctor") val doctor : String,
    @SerializedName("Date") val date : String,
    @SerializedName("PeriodOfTime") val periodOfTime : String
)
