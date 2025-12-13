package com.tech.vexilmachineround.adapter

import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.ItemInspectionImageBinding
import com.tech.vexilmachineround.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class InspectionAdapter : RecyclerView.Adapter<InspectionAdapter.InspectionViewHolder>() {
    private var images: List<Image> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InspectionViewHolder {
        val binding = ItemInspectionImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InspectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InspectionViewHolder,
        position: Int
    ) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun submitList(newImages: List<Image>) {
        val diffCallback = ImageDiffCallback(images, newImages)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        images = newImages
        diffResult.dispatchUpdatesTo(this)
    }

    class InspectionViewHolder(val binding: ItemInspectionImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.tvImageTitleLabel.text = image.title
            binding.tvCapturedAtLabel.text = image.capturedAt
            Glide.with(binding.root)
                .load(image.fileUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.ivInspectionImage)

            binding.tvPlaceLabel.text = "Fetching location..."

            //fetch location from coroutine
            CoroutineScope(Dispatchers.Main).launch {
                val address = getAddressFromCoordinates(image.latitude, image.longitude)
                binding.tvPlaceLabel.text = address
            }
        }

        private suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String {
            return withContext(Dispatchers.IO) {
                try {
                    val geocoder = Geocoder(binding.root.context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses?.isNotEmpty() == true) {
                        val address = addresses[0]
                        //we can format this address as needed
                        address.getAddressLine(0)
                    } else {
                        "Location not found"
                    }
                } catch (e: IOException) {
                    // This error often means no network connection or the geocoder service is unavailable.
                    "Error fetching address: ${e.message}"
                }
            }
        }

    }

    class ImageDiffCallback(
        private val oldList: List<Image>,
        private val newList: List<Image>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].fileUrl == newList[newItemPosition].fileUrl
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
