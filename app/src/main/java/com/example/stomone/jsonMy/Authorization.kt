package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class Authorization(
    @SerializedName("Surname") val surname : String,
    @SerializedName("Name") val name : String,
    @SerializedName("Patronymic") val patronymic : String,
    @SerializedName("Password") val password : String

)


