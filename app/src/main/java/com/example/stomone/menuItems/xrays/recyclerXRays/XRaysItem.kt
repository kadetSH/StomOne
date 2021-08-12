package com.example.stomone.menuItems.xrays.recyclerXRays

import java.io.Serializable

data class XRaysItem(
    var datePhoto : String,
    var numberDirection : String,
    var typeOfResearch : String,
    var financing : String,
    var teeth : String,
    var doctor : String
): Serializable
