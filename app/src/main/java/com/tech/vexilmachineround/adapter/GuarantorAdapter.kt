package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.ItemGuarantorBinding
import com.tech.vexilmachineround.model.Guarantor

class GuarantorAdapter : RecyclerView.Adapter<GuarantorAdapter.GuarantorViewHolder>() {

    private var guarantors: List<Guarantor> = emptyList()
    private val expandedItems = mutableSetOf<Int>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Guarantor>) {
        guarantors = list
        expandedItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuarantorViewHolder {
        val binding =
            ItemGuarantorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuarantorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuarantorViewHolder, position: Int) {
        val guarantor = guarantors[position]
        holder.bind(guarantor, position)
    }

    override fun getItemCount(): Int = guarantors.size

    inner class GuarantorViewHolder(private val binding: ItemGuarantorBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        private val name: TextView = itemView.findViewById(R.id.tvGuarantorName)
//        private val relationship: TextView = itemView.findViewById(R.id.tvGuarantorRelationship)
//        private val details: TextView = itemView.findViewById(R.id.tvGuarantorDetails)
//        private val header: ConstraintLayout = itemView.findViewById(R.id.clGuarantorHeader)
//        private val detailsGroup: Group = itemView.findViewById(R.id.guarantorDetailsGroup)
//        private val expandIcon: ImageView = itemView.findViewById(R.id.ivExpandCollapse)

        fun bind(guarantor: Guarantor, position: Int) {
            binding.tvGuarantorName.text = guarantor.name
            binding.tvGuarantorRelationship.text = guarantor.relationship
            binding.tvGuarantorDetails.text =
                "Check Status: ${guarantor.checkStatus}\nVerified: ${guarantor.verified}"

            val isExpanded = expandedItems.contains(position)
            binding.guarantorDetailsGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.ivExpandCollapse.setImageResource(if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

            binding.clGuarantorHeader.setOnClickListener {
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