package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class JSNews(
    @SerializedName("Title") val title : String,
    @SerializedName("Content") val content : String,
    @SerializedName("ImagePath") val imagePath : String
)
