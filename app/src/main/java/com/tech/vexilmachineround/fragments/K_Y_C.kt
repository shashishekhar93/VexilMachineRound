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
import com.bumptech.glide.Glide
import com.tech.vexilmachineround.R
import com.tech.vexilmachineround.databinding.FragmentKycBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import kotlinx.coroutines.launch

class K_Y_C : Fragment() {

    private var _binding: FragmentKycBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JsonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentKycBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUIState()
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.sampleResponseData.collect { result ->
                    when(result){
                        is ApiResult.Success ->{
                            val obj = result.data
                            binding.tvAadhaarNumber.text = obj.data.memberDetails.kyc.aadhaar.number
                            binding.tvPanNumber.text = obj.data.memberDetails.kyc.pan.number
                            Glide.with(requireContext())
                                .load(obj.data.memberDetails.kyc.aadhaar.imageFront)
                                .placeholder(R.drawable.ic_placeholder)
                                .into(binding.ivAadhaarFront)

                            Glide.with(requireContext())
                                .load(obj.data.memberDetails.kyc.aadhaar.imageBack)
                                .placeholder(R.drawable.ic_placeholder)
                                .into(binding.ivAadhaarBack)

                            Glide.with(requireContext())
                                .load(obj.data.memberDetails.kyc.pan.image)
                                .placeholder(R.drawable.ic_placeholder)
                                .into(binding.ivPanImage)

                            if (obj.data.memberDetails.kyc.aadhaar.verified){
                                binding.chipAadhaarVerified.visibility = View.VISIBLE
                                binding.chipPanVerified.visibility = View.VISIBLE
                                }else{
                                binding.chipAadhaarVerified.visibility = View.GONE
                            }

                            if (obj.data.memberDetails.kyc.pan.verified){
                                binding.chipAadhaarVerified.visibility = View.VISIBLE
                                binding.chipPanVerified.visibility = View.VISIBLE
                            }else{
                                binding.chipAadhaarVerified.visibility = View.GONE
                            }



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