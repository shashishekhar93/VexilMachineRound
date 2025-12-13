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
import com.tech.vexilmachineround.adapter.DocumentAdapter
import com.tech.vexilmachineround.databinding.FragmentDocumentsBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Documents : Fragment() {
    private var _binding: FragmentDocumentsBinding? = null
    private val binding: FragmentDocumentsBinding get() = _binding!!
    private val viewModel: JsonViewModel by activityViewModels()
    @Inject
    lateinit var documentAdapter: DocumentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDocument.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = documentAdapter
        }

        observeUIState()
    }

    private fun observeUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            val obj = result.data.data.documents
                            documentAdapter.updateList(obj)
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