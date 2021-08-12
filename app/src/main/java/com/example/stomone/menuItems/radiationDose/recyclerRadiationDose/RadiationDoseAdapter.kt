package com.example.stomone.menuItems.radiationDose.recyclerRadiationDose

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class RadiationDoseAdapter(private val layoutInflater: LayoutInflater,
                           private val itemsArray: ArrayList<RadiationDoseItem>):  RecyclerView.Adapter<RadiationDoseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadiationDoseViewHolder {
        return RadiationDoseViewHolder(layoutInflater.inflate(R.layout.template_radiation_dose, parent, false))
    }

    override fun onBindViewHolder(holder: RadiationDoseViewHolder, position: Int) {
        holder.bind(itemsArray[position])
    }

    override fun getItemCount(): Int = itemsArray.size
}