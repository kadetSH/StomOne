package com.example.stomone.recyclerOfficeHours

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R


class OfficeHoursViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var doctorFIO: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_doctor)
    var doctorProfession: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_profession)
    var linearLayout1 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout1)
    var day1: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC00)
    var date1: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC00)
    var reception1: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC00)
    var linearLayout2 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout2)
    var day2: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC01)
    var date2: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC01)
    var reception2: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC01)
    var linearLayout3 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout3)
    var day3: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC02)
    var date3: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC02)
    var reception3: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC02)
    var linearLayout4 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout4)
    var day4: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC10)
    var date4: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC10)
    var reception4: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC10)
    var linearLayout5 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout5)
    var day5: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC11)
    var date5: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC11)
    var reception5: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC11)
    var linearLayout6 :LinearLayout = itemView.findViewById<LinearLayout>(R.id.id_oh_linear_layout6)
    var day6: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_day_RC22)
    var date6: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_date_RC22)
    var reception6: TextView = itemView.findViewById<TextView>(R.id.id_oh_edit_reception_RC22)

    fun bind(item: OfficeHoursItem) {

        val receptionStr = "Нет приема"

        doctorFIO.text = item.doctorFIO
        doctorProfession.text = item.doctorProfession
        day1.text = item.day1

        if (item.reception1 != receptionStr){
            date1.setTextColor(Color.BLACK)
            linearLayout1.setBackgroundResource(R.drawable.color_frame)
        }
        if (item.reception2 != receptionStr){
            date2.setTextColor(Color.BLACK)
            linearLayout2.setBackgroundResource(R.drawable.color_frame)
        }
        if (item.reception3 != receptionStr){
            date3.setTextColor(Color.BLACK)
            linearLayout3.setBackgroundResource(R.drawable.color_frame)
        }
        if (item.reception4 != receptionStr){
            date4.setTextColor(Color.BLACK)
            linearLayout4.setBackgroundResource(R.drawable.color_frame)
        }
        if (item.reception5 != receptionStr){
            date5.setTextColor(Color.BLACK)
            linearLayout5.setBackgroundResource(R.drawable.color_frame)
        }
        if (item.reception6 != receptionStr){
            date6.setTextColor(Color.BLACK)
            linearLayout6.setBackgroundResource(R.drawable.color_frame)
        }

        date1.text = item.date1
        reception1.text = item.reception1
        day2.text = item.day2
        date2.text = item.date2
        reception2.text = item.reception2
        day3.text = item.day3
        date3.text = item.date3
        reception3.text = item.reception3
        day4.text = item.day4
        date4.text = item.date4
        reception4.text = item.reception4
        day5.text = item.day5
        date5.text = item.date5
        reception5.text = item.reception5
        day6.text = item.day6
        date6.text = item.date6
        reception6.text = item.reception6

    }


}