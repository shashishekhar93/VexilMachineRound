package com.tech.vexilmachineround.fragments

import android.annotation.SuppressLint
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
import com.tech.vexilmachineround.adapter.CoApplicantAdapter
import com.tech.vexilmachineround.adapter.EmiAdapter
import com.tech.vexilmachineround.adapter.GuarantorAdapter
import com.tech.vexilmachineround.adapter.ReferenceContactAdapter
import com.tech.vexilmachineround.databinding.FragmentLoanSummaryBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoanSummary : Fragment() {

    private var _binding: FragmentLoanSummaryBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var emiAdapter: EmiAdapter
    @Inject
    lateinit var coApplicantAdapter: CoApplicantAdapter
    @Inject
    lateinit var guarantorAdapter: GuarantorAdapter
    @Inject
    lateinit var referenceContactAdapter: ReferenceContactAdapter
    private val viewModel: JsonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeUIState()
    }

    @SuppressLint("SetTextI18n")
    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val loan = result.data.data.loan
                            binding.tvLoanProduct.text = loan.product
                            binding.tvLoanId.text = "ID: ${loan.loanId}"
                            binding.tvStatus.text = loan.status
                            binding.tvAmountRequested.text = "₹ ${loan.amountRequested}"
                            binding.tvInterestRate.text = "${loan.interestRate} %"
                            binding.tvTenure.text = "${loan.tenure} months"
                            binding.tvSubmittedAt.text = loan.submittedAt
                            emiAdapter.submitList(loan.emis)
                            coApplicantAdapter.submitList(loan.coApplicants)
                            guarantorAdapter.submitList(loan.guarantors)
                            referenceContactAdapter.submitList(loan.referenceContacts)
                        }

                        else -> {
                            // Handle other states if needed
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rvEmis.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = emiAdapter
        }
        binding.rvCoApplicants.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coApplicantAdapter
        }
        binding.rvGuarantors.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = guarantorAdapter
        }
        binding.rvReferences.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = referenceContactAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}