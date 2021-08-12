package com.example.stomone.menuItems.picturesVisit.recyclerPicturesVisit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class PicturesVisitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var dateOfReceipt: TextView = itemView.findViewById<TextView>(R.id.template_pictures_visit_edit_date)
    var numberPicture: TextView = itemView.findViewById<TextView>(R.id.template_pictures_visit_edit_number)
    var doctor: TextView = itemView.findViewById<TextView>(R.id.template_pictures_visit_edit_doctor)
    var loader: ImageView = itemView.findViewById<ImageView>(R.id.template_pictures_visit_loader)

    fun bind(item: PicturesVisitItem) {
        dateOfReceipt.text = item.dateOfReceipt
        numberPicture.text = item.numberPicture
        doctor.text = item.doctor
    }
}