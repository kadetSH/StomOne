package com.example.stomone.menuItems.schedule.departmentDoctors.recyclerOfficeHours

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class OfficeHoursAdapter(
    private val layoutInflater: LayoutInflater,
    private val itemsArray: ArrayList<OfficeHoursItem>,
    private val listener: ((officeHoursItem: OfficeHoursItem, position: Int, date: String, reception: String) -> Unit)?
) : RecyclerView.Adapter<OfficeHoursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeHoursViewHolder {
        return OfficeHoursViewHolder(
            layoutInflater.inflate(
                R.layout.template_office_hours3,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OfficeHoursViewHolder, position: Int) {
        holder.bind(itemsArray[position])

        holder.date1.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date1.text.toString(),
                holder.reception1.text.toString()
            )

        }
        holder.date2.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date2.text.toString(),
                holder.reception2.text.toString()
            )

        }
        holder.date3.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date3.text.toString(),
                holder.reception3.text.toString()
            )
        }
        holder.date4.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date4.text.toString(),
                holder.reception4.text.toString()
            )
        }
        holder.date5.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date5.text.toString(),
                holder.reception5.text.toString()
            )
        }
        holder.date6.setOnClickListener {
            listener?.invoke(
                itemsArray[position],
                position,
                holder.date6.text.toString(),
                holder.reception6.text.toString()
            )
        }
    }

    override fun getItemCount(): Int = itemsArray.size
}