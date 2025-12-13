package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_co_applicant, parent, false)
        return CoApplicantViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoApplicantViewHolder, position: Int) {
        val coApplicant = coApplicants[position]
        holder.bind(coApplicant, position)
    }

    override fun getItemCount(): Int = coApplicants.size

    inner class CoApplicantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvCoApplicantName)
        private val relationship: TextView = itemView.findViewById(R.id.tvCoApplicantRelationship)
        private val details: TextView = itemView.findViewById(R.id.tvCoApplicantDetails)
        private val header: ConstraintLayout = itemView.findViewById(R.id.clCoApplicantHeader)
        private val detailsGroup: Group = itemView.findViewById(R.id.coApplicantDetailsGroup)
        private val expandIcon: ImageView = itemView.findViewById(R.id.ivExpandCollapse)

        fun bind(coApplicant: CoApplicant, position: Int) {
            name.text = coApplicant.name
            relationship.text = coApplicant.relationship
            details.text = "Monthly Income: ${coApplicant.incomeMonthly}\nDOB: ${coApplicant.dob}\nKYC Status: ${coApplicant.kycStatus}\nMobile: ${coApplicant.mobile}\nOccupation: ${coApplicant.occupation}"

            val isExpanded = expandedItems.contains(position)
            detailsGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE
            expandIcon.setImageResource(if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

            header.setOnClickListener {
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