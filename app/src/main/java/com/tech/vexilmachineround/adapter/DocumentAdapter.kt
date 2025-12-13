package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.vexilmachineround.databinding.ItemDocumentBinding
import com.tech.vexilmachineround.model.Document

class DocumentAdapter : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

    private var documentList: List<Document> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Document>) {
        documentList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocumentViewHolder {
        val binding = ItemDocumentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DocumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document = documentList[position]
        holder.bind(document)
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    class DocumentViewHolder(private val binding: ItemDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(document: Document) {
            binding.tvDocumentType.text = document.docType
            binding.tvUploadedAt.text = document.uploadedAt
            Glide.with(binding.root).load(document.fileUrl).into(binding.ivDocument)
        }
    }
}