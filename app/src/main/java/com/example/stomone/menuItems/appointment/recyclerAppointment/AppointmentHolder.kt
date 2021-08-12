package com.example.stomone.menuItems.appointment.recyclerAppointment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class AppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val requestNumber: TextView = itemView.findViewById<TextView>(R.id.template_appointment_title)
    private val doctorFIO: TextView = itemView.findViewById<TextView>(R.id.template_appointment_doctor_edit)
    private val doctorProfession: TextView = itemView.findViewById<TextView>(R.id.template_appointment_profession_edit)
    private val dateRequest: TextView = itemView.findViewById<TextView>(R.id.template_appointment_date_edit)
    private val timeStart: TextView = itemView.findViewById<TextView>(R.id.template_appointment_time_start_edit)
    private val timeEnd: TextView = itemView.findViewById<TextView>(R.id.template_appointment_time_end_edit)

    fun bind(item: AppointmentItem){
        requestNumber.text = item.requestNumber
        doctorFIO.text = item.doctorFIO
        doctorProfession.text = item.doctorProfession
        dateRequest.text = item.dateRequest
        timeStart.text = item.timeStart
        timeEnd.text = item.timeEnd
    }

}