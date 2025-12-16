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
import com.tech.vexilmachineround.utils.AppUtils
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
                        is ApiResult.Loading -> {
                            binding.shimmerLayout.startShimmer()
                            //binding.shimmerLayout.visibility = View.VISIBLE
                        }
                        is ApiResult.Success ->{
                            binding.shimmerLayout.stopShimmer()
                            //binding.shimmerLayout.visibility = View.GONE
                            val obj = result.data
                            binding.tvAadhaarNumber.text = obj.data.memberDetails.kyc.aadhaar.number
                            binding.tvPanNumber.text = obj.data.memberDetails.kyc.pan.number

                            AppUtils.ImageUtils.loadIbbCoImage(
                                context = requireContext(),
                                imageView = binding.ivAadhaarFront,
                                galleryUrl = obj.data.memberDetails.kyc.aadhaar.imageFront
                            )

                            AppUtils.ImageUtils.loadIbbCoImage(
                                context = requireContext(),
                                imageView = binding.ivAadhaarBack,
                                galleryUrl = obj.data.memberDetails.kyc.aadhaar.imageBack
                            )

                            AppUtils.ImageUtils.loadIbbCoImage(
                                context = requireContext(),
                                imageView = binding.ivPanImage,
                                galleryUrl = obj.data.memberDetails.kyc.pan.image
                            )

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

                        is ApiResult.Error -> {
                            binding.shimmerLayout.stopShimmer()
                            //binding.shimmerLayout.visibility = View.GONE
                        }
                        else -> {
                            binding.shimmerLayout.stopShimmer()
                            //binding.shimmerLayout.visibility = View.GONE
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