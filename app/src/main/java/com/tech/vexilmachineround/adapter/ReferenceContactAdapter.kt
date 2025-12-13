package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.model.ReferenceContact

class ReferenceContactAdapter : RecyclerView.Adapter<ReferenceContactAdapter.ReferenceViewHolder>() {

    private var references: List<ReferenceContact> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ReferenceContact>) {
        references = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reference, parent, false)
        return ReferenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReferenceViewHolder, position: Int) {
        val reference = references[position]
        holder.bind(reference)
    }

    override fun getItemCount(): Int = references.size

    inner class ReferenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvReferenceName)
        private val relationship: TextView = itemView.findViewById(R.id.tvReferenceRelationship)
        private val mobile: TextView = itemView.findViewById(R.id.tvReferenceMobile)

        fun bind(reference: ReferenceContact) {
            name.text = reference.name
            relationship.text = reference.relation
            mobile.text = reference.mobile
        }
    }
}