package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.databinding.ItemAuditTrailBinding
import com.tech.vexilmachineround.model.AuditTrail
import com.tech.vexilmachineround.model.Document

class AuditTrailAdapter() : RecyclerView.Adapter<AuditTrailAdapter.ViewHolder>() {

    private var trailList: List<AuditTrail> = emptyList()


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<AuditTrail>) {
        trailList = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAuditTrailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = trailList[position]
        holder.bind(item, position, itemCount)
    }

    override fun getItemCount() = trailList.size

    class ViewHolder(private val binding: ItemAuditTrailBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: AuditTrail, position: Int, count: Int) {
            binding.actionText.text = item.action
            binding.actorText.text = "By: ${item.actor}"
            binding.timestampText.text = "Time: ${item.timestamp}"

            if (position == 0) {
                binding.timelineLineTop.visibility = View.INVISIBLE
            } else {
                binding.timelineLineTop.visibility = View.VISIBLE
            }

            if (position == count - 1) {
                binding.timelineLineBottom.visibility = View.INVISIBLE
            } else {
                binding.timelineLineBottom.visibility = View.VISIBLE
            }
        }
    }
}