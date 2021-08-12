package com.example.stomone.menuItems.visitHistory.recyclerVisitHistory

import java.io.Serializable

data class VisitHistoryItem(
    var service : String,
    var dateOfService : String,
    var type : String,
    var count : String,
    var sum : String,
    var doctor : String
): Serializable