package com.example.stomone.menuItems.appointment.recyclerAppointment

import java.io.Serializable

data class AppointmentItem(
    var requestNumber: String,
    var doctorFIO: String,
    var doctorProfession: String,
    var dateRequest: String,
    var timeStart: String,
    var timeEnd: String
) : Serializable
