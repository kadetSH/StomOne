package com.example.stomone.recyclerVisitHistory

import java.io.Serializable

data class VisitHistoryItem(
    var service : String,
    var dateOfService : String,
    var type : String,
    var count : String,
    var sum : String,
    var doctor : String
): Serializable