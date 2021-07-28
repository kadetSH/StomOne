package com.example.stomone.recyclerBusinessHours

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class BusinessHoursHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var timeTitle: TextView = itemView.findViewById<TextView>(R.id.id_bh_edit_time)

    fun bind(item: BusinessHoursItem) {
        timeTitle.text = item.time

        if (item.periodOfTime == 1) {
            timeTitle.setBackgroundColor(Color.YELLOW)
        }else{
            timeTitle.setBackgroundColor(Color.GRAY)
        }
    }

}