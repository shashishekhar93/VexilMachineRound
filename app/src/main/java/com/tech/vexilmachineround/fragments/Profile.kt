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
import com.tech.vexilmachineround.databinding.FragmentProfileBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import kotlinx.coroutines.launch

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JsonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUIState()
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            //
                            val obj = result.data
                            binding.tvFullName.text = obj.data.memberDetails.fullName
                            binding.tvMemberId.text = obj.data.memberDetails.memberId
                            binding.tvPhone.text = obj.data.memberDetails.mobile
                            binding.tvEmail.text = obj.data.memberDetails.email
                            binding.tvAvatar.text = getInitials(obj.data.memberDetails.fullName)
                            binding.tvType.text = obj.data.memberDetails.type
                            binding.maritalStatus.text = obj.data.memberDetails.maritalStatus
                            binding.tvGender.text = obj.data.memberDetails.gender


                        }

                        else -> {
                            // Handle other states if needed
                        }
                    }
                }
            }
        }
    }

    private fun getInitials(fullName:String?): String{
        if (fullName.isNullOrBlank()) return "NA"
        val names = fullName.trim().split(' ').filter { it.isNotEmpty() }
        return when{
            names.size>1-> "${names.first().first()}${names.last().first()}"
            names.isNotEmpty() ->names.first().take(1)
            else-> "NA"
        }.uppercase()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}