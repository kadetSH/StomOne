package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class CreateAnAppointmentJS(
    @SerializedName("PatientUI") val patientUI : String,
    @SerializedName("DoctorFIO") val doctorFIO : String,
    @SerializedName("DateOfReceipt") val dateOfReceipt : String,
    @SerializedName("TimeOfReceipt") val timeOfReceipt : String
)
