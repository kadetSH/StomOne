package com.example.stomone.jsonMy

import com.google.gson.annotations.SerializedName

data class OfficeHoursJS(
    @SerializedName("Doctor") val doctor : String,
    @SerializedName("Profession") val profession : String,
    @SerializedName("Schedule") val schedule : List<ScheduleJS>
)


data class ScheduleJS(
    @SerializedName("Date") val date : String,
    @SerializedName("DayOfTheWeek") val dayOfTheWeek : String,
    @SerializedName("Reception") val reception : String
)


