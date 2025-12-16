package com.tech.vexilmachineround.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.ItemDocumentBinding
import com.tech.vexilmachineround.model.Document
import com.tech.vexilmachineround.utils.AppUtils

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
            AppUtils.ImageUtils.loadIbbCoImage(
                context = binding.root.context,
                imageView = binding.ivDocument,
                galleryUrl = document.fileUrl
            )
        }
    }
}