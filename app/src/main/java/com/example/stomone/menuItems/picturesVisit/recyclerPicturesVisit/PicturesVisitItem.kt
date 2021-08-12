package com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit

import java.io.Serializable

data class PicturesVisitItem(
    var dateOfReceipt : String,
    var numberPicture : String,
    var doctor : String
): Serializable
