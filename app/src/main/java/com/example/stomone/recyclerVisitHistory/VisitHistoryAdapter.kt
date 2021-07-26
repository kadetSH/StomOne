package com.example.stomone.recyclerVisitHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R


class VisitHistoryAdapter(private val layoutInflater: LayoutInflater,
                          private val itemsArray: ArrayList<VisitHistoryItem>):  RecyclerView.Adapter<VisitHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitHistoryHolder {
        return VisitHistoryHolder(layoutInflater.inflate(R.layout.template_visit_history, parent, false))
    }

    override fun onBindViewHolder(holder: VisitHistoryHolder, position: Int) {
        holder.bind(itemsArray[position])
    }

    override fun getItemCount(): Int = itemsArray.size


}