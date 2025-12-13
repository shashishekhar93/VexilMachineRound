package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.model.Emi

class EmiAdapter : RecyclerView.Adapter<EmiAdapter.EmiViewHolder>() {

    private var emis: List<Emi> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Emi>) {
        emis = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emi, parent, false)
        return EmiViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmiViewHolder, position: Int) {
        val emi = emis[position]
        holder.bind(emi)
    }

    override fun getItemCount(): Int = emis.size

    inner class EmiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val schedule: TextView = itemView.findViewById(R.id.tvEmiSchedule)
        private val amount: TextView = itemView.findViewById(R.id.tvEmiAmount)
        private val status: TextView = itemView.findViewById(R.id.tvEmiStatus)

        fun bind(emi: Emi) {
            schedule.text = emi.schedule
            amount.text = "₹${emi.amount}"
            status.text = emi.status
        }
    }
}