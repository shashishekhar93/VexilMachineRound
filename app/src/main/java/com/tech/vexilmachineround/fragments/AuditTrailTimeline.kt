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
import com.tech.vexilmachineround.adapter.AuditTrailAdapter
import com.tech.vexilmachineround.databinding.FragmentAuditTrailTimelineBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuditTrailTimeline : Fragment() {

    @Inject
    lateinit var adapter: AuditTrailAdapter

    private val viewmodel: JsonViewModel by activityViewModels()
    private var _binding: FragmentAuditTrailTimelineBinding? = null
    private val binding: FragmentAuditTrailTimelineBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAuditTrailTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.auditTrailRecyclerView.apply {
            adapter = this@AuditTrailTimeline.adapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

        observeUIState()
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val obj = result.data.data.auditTrail
                            adapter.updateList(obj)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}