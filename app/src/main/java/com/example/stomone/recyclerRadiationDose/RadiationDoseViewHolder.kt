package com.example.stomone.recyclerRadiationDose

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R
import com.example.stomone.recyclerPicturesVisit.PicturesVisitItem

class RadiationDoseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var date: TextView = itemView.findViewById<TextView>(R.id.template_radiation_dose_edit_date)
    var teeth: TextView = itemView.findViewById<TextView>(R.id.template_radiation_dose_edit_teeth)
    var radiationDose: TextView = itemView.findViewById<TextView>(R.id.template_radiation_dose_edit_dose)
    var typeOfResearch: TextView = itemView.findViewById<TextView>(R.id.template_radiation_dose_edit_type_of_research)
    var doctor: TextView = itemView.findViewById<TextView>(R.id.template_radiation_dose_edit_doctor)

    fun bind(item: RadiationDoseItem) {
        date.text = item.date
        teeth.text = item.teeth
        radiationDose.text = item.radiationDose
        typeOfResearch.text = item.typeOfResearch
        doctor.text = item.doctor
    }


}