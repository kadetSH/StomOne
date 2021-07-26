package com.example.stomone.recyclerPicturesVisit

import java.io.Serializable

data class PicturesVisitItem(
    var dateOfReceipt : String,
    var numberPicture : String,
    var doctor : String
): Serializable
