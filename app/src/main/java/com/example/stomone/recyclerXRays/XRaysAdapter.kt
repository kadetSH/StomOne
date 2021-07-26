package com.example.stomone.recyclerXRays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class XRaysAdapter(private val layoutInflater: LayoutInflater,
                   private val itemsArray: ArrayList<XRaysItem>,
                   private val listener: ((xRaysItem: XRaysItem, position: Int) -> Unit)?):  RecyclerView.Adapter<XRaysHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XRaysHolder {
        return XRaysHolder(layoutInflater.inflate(R.layout.template_x_rays, parent, false))
    }

    override fun onBindViewHolder(holder: XRaysHolder, position: Int) {
        holder.bind(itemsArray[position])

        holder.loader.setOnClickListener {
            listener?.invoke(itemsArray[position], position)
        }
    }

    override fun getItemCount(): Int = itemsArray.size

}