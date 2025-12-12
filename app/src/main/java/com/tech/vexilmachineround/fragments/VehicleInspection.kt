package com.tech.vexilmachineround.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.vexilmachineround.adapter.InspectionAdapter
import com.tech.vexilmachineround.databinding.FragmentVehicleInspectionBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VehicleInspection : Fragment() {

    @Inject
    lateinit var adapter: InspectionAdapter

    private var _binding: FragmentVehicleInspectionBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: JsonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleInspectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvInspectionImages.apply {
            // Use the adapter injected by Hilt
            this.adapter = this@VehicleInspection.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val obj = result.data
                            binding.tvTypeValue.text = obj.data.vehicle.type
                            binding.tvMakeValue.text = obj.data.vehicle.make
                            binding.tvModelValue.text = obj.data.vehicle.model
                            binding.tvRegistrationNumberValue.text =
                                obj.data.vehicle.registrationNumber
                            binding.tvRegistrationDateValue.text = obj.data.vehicle.registrationDate
                            binding.tvStatusValue.text = obj.data.vehicle.inspection.status
                            
                            // Submit the list to the adapter. 
                            // This will update the RecyclerView because we're now using the correct adapter instance.
                            adapter.submitList(obj.data.vehicle.inspection.images)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
