package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSPicturesVisit(
    @SerializedName("DateOfReceipt") val dateOfReceipt : String,
    @SerializedName("NumberPicture") val numberPicture : String,
    @SerializedName("Doctor") val doctor : String
)
