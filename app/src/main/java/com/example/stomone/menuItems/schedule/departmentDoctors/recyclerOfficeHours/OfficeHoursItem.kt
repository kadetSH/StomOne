package com.example.stomone.menuItems.schedule.departmentDoctors.recyclerOfficeHours

import java.io.Serializable

data class OfficeHoursItem(
    var doctorFIO : String,
    var doctorProfession : String,
    var day1 : String,
    var date1 : String,
    var reception1 : String,
    var day2 : String,
    var date2 : String,
    var reception2 : String,
    var day3 : String,
    var date3 : String,
    var reception3 : String,
    var day4 : String,
    var date4 : String,
    var reception4 : String,
    var day5 : String,
    var date5 : String,
    var reception5 : String,
    var day6 : String,
    var date6 : String,
    var reception6 : String
): Serializable
