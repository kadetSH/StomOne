package com.example.stomone.recyclerPicturesVisit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class PicturesVisitAdapter(private val layoutInflater: LayoutInflater,
                           private val itemsArray: ArrayList<PicturesVisitItem>,
                           private val listener: ((item: PicturesVisitItem) -> Unit)?
                           ):  RecyclerView.Adapter<PicturesVisitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesVisitViewHolder {
        return PicturesVisitViewHolder(layoutInflater.inflate(R.layout.template_pictures_visit, parent, false))
    }

    override fun onBindViewHolder(holder: PicturesVisitViewHolder, position: Int) {
        holder.bind(itemsArray[position])

        holder.loader.setOnClickListener {
            listener?.invoke(itemsArray[position])
        }
    }

    override fun getItemCount(): Int = itemsArray.size
}