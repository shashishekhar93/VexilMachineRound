package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.ItemEmiBinding
import com.tech.vexilmachineround.model.Emi

class EmiAdapter : RecyclerView.Adapter<EmiAdapter.EmiViewHolder>() {

    private var emis: List<Emi> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Emi>) {
        emis = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmiViewHolder {
        val binding = ItemEmiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmiViewHolder, position: Int) {
        val emi = emis[position]
        holder.bind(emi)
    }

    override fun getItemCount(): Int = emis.size

    inner class EmiViewHolder(private val binding: ItemEmiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(emi: Emi) {
            binding.tvEmiSchedule.text = emi.schedule
            binding.tvEmiAmount.text = "₹${emi.amount}"
            binding.tvEmiStatus.text = emi.status
        }
    }
}