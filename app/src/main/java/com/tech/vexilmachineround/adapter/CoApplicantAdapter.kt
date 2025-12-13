package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.ItemCoApplicantBinding
import com.tech.vexilmachineround.model.CoApplicant

class CoApplicantAdapter : RecyclerView.Adapter<CoApplicantAdapter.CoApplicantViewHolder>() {

    private var coApplicants: List<CoApplicant> = emptyList()
    private val expandedItems = mutableSetOf<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CoApplicant>) {
        coApplicants = list
        expandedItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoApplicantViewHolder {
        val binding =
            ItemCoApplicantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoApplicantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoApplicantViewHolder, position: Int) {
        val coApplicant = coApplicants[position]
        holder.bind(coApplicant, position)
    }

    override fun getItemCount(): Int = coApplicants.size

    inner class CoApplicantViewHolder(private val binding: ItemCoApplicantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(coApplicant: CoApplicant, position: Int) {
            binding.tvCoApplicantName.text = coApplicant.name
            binding.tvCoApplicantRelationship.text = coApplicant.relationship
            binding.tvCoApplicantDetails.text =
                "Monthly Income: ${coApplicant.incomeMonthly}\nDOB: ${coApplicant.dob}\nKYC Status: ${coApplicant.kycStatus}\nMobile: ${coApplicant.mobile}\nOccupation: ${coApplicant.occupation}"

            val isExpanded = expandedItems.contains(position)
            binding.coApplicantDetailsGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.ivExpandCollapse.setImageResource(if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

            binding.clCoApplicantHeader.setOnClickListener {
                if (expandedItems.contains(position)) {
                    expandedItems.remove(position)
                } else {
                    expandedItems.add(position)
                }
                notifyItemChanged(position)
            }
        }
    }
}