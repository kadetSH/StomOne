package com.example.stomone.recyclerContracts

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class ContractsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var contractTitle: TextView = itemView.findViewById<TextView>(R.id.template_contract_title)
    var contractData: TextView = itemView.findViewById<TextView>(R.id.template_contract_edit_data)
    var contractFinish: CheckBox = itemView.findViewById<CheckBox>(R.id.template_contract_check_box)
    var contractDoctor: TextView = itemView.findViewById<TextView>(R.id.template_contract_edit_doctor)

    fun bind(item: ContractItem) {
        contractTitle.text = item.contractTitle
        contractData.text = item.contractData
        contractFinish.isChecked = item.contractCheckBox
        contractDoctor.text = item.contractDoctor
    }

}