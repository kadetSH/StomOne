package com.example.stomone.recyclerAppointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R
import com.example.stomone.recyclerXRays.XRaysHolder
import com.example.stomone.recyclerXRays.XRaysItem

class AppointmentAdapter(
    private val layoutInflater: LayoutInflater,
    private val itemsArray: ArrayList<AppointmentItem>
) : RecyclerView.Adapter<AppointmentHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        return AppointmentHolder(layoutInflater.inflate(R.layout.template_appointment, parent, false))
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.bind(itemsArray[position])
    }

    override fun getItemCount(): Int = itemsArray.size
}