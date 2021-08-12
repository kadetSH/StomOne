package com.example.stomone.menuItems.xrays.recyclerXRays

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class XRaysHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val editDate: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_date)
    private val editNumber: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_number)
    private val editType: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_type_of_research)
    private val editFinancing: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_financing)
    private val editTeeth: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_teeth)
    private val editDoctor: TextView = itemView.findViewById<TextView>(R.id.template_x_rays_edit_doctor)
    val loader: ImageView = itemView.findViewById<ImageView>(R.id.template_x_rays_image_loader)

    fun bind(item: XRaysItem){
        editDate.text = item.datePhoto
        editNumber.text = item.numberDirection
        editType.text = item.typeOfResearch
        editFinancing.text = item.financing
        editTeeth.text = item.teeth
        editDoctor.text = item.doctor
    }

}