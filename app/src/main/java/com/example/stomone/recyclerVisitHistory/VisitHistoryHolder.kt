package com.example.stomone.recyclerVisitHistory

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R
import com.example.stomone.recyclerContracts.ContractItem

class VisitHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    var editService: TextView = itemView.findViewById<TextView>(R.id.id_vh_service)
    var editDate: TextView = itemView.findViewById<TextView>(R.id.id_vh_date)
    var editType: TextView = itemView.findViewById<TextView>(R.id.id_vh_edit_type)
    var editCount: TextView = itemView.findViewById<TextView>(R.id.id_vh_edit_count)
    var editSum: TextView = itemView.findViewById<TextView>(R.id.id_vh_edit_sum)
    var editDoctor: TextView = itemView.findViewById<TextView>(R.id.id_vh_edit_doctor)

    fun bind(item: VisitHistoryItem) {

        editService.text = item.service
        editDate.text = item.dateOfService
        editType.text = item.type
        editCount.text = item.count
        editSum.text = item.sum
        editDoctor.text = item.doctor

    }

}