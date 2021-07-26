package com.example.stomone.recyclerBusinessHours

import java.io.Serializable

data class BusinessHoursItem(
    var time: String,
    var periodOfTime: Int
) : Serializable
