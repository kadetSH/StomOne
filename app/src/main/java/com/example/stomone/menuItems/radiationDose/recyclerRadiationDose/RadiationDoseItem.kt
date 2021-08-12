package com.example.stomone.menuItems.radiationDose.recyclerRadiationDose

import java.io.Serializable

data class RadiationDoseItem(
    var date : String,
    var teeth : String,
    var typeOfResearch : String,
    var radiationDose : String,
    var doctor : String
): Serializable