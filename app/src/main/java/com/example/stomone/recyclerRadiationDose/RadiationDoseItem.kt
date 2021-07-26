package com.example.stomone.recyclerRadiationDose

import java.io.Serializable

data class RadiationDoseItem(
    var date : String,
    var teeth : String,
    var typeOfResearch : String,
    var radiationDose : String,
    var doctor : String
): Serializable