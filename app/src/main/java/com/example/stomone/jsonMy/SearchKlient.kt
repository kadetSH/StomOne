package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class SearchKlient(
    @SerializedName("Surname") val surname : String,
    @SerializedName("Name") val name : String,
    @SerializedName("Patronymic") val patronymic : String,
    @SerializedName("Telephone") val telephone : String,
    @SerializedName("Birth") val Birth : String
)
