package com.example.stomone.menuItems.schedule.businesHours.recyclerBusinessHours

import java.io.Serializable

data class BusinessHoursItem(
    var time: String,
    var periodOfTime: Int
) : Serializable
