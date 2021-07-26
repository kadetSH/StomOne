package com.example.stomone.recyclerContracts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stomone.R

class ContractsAdapter(private val layoutInflater: LayoutInflater,
                       private val itemsArray: ArrayList<ContractItem>):  RecyclerView.Adapter<ContractsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractsViewHolder {
        return ContractsViewHolder(layoutInflater.inflate(R.layout.template_contracts, parent, false))
    }

    override fun onBindViewHolder(holder: ContractsViewHolder, position: Int) {
        holder.bind(itemsArray[position])
    }

    override fun getItemCount(): Int = itemsArray.size

    fun setItems(rep : ArrayList<ContractItem>) {
        itemsArray.clear()
        itemsArray.addAll(rep)
        notifyDataSetChanged()
    }

}