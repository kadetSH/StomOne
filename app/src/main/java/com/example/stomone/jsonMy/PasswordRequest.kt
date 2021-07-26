package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("Surname") val surname : String,
    @SerializedName("Name") val name : String,
    @SerializedName("Patronymic") val patronymic : String,
    @SerializedName("Gender") val gender : String,
    @SerializedName("Result") val result : String
)
