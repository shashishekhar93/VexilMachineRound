package com.tech.vexilmachineround.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tech.vexilmachineround.adapter.HomePagerAdapter
import com.tech.vexilmachineround.databinding.ActivityHomeBinding
import com.tech.vexilmachineround.utils.ApiResult
import com.tech.vexilmachineround.viewmodel.JsonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: JsonViewModel by viewModels()

    @Inject
    lateinit var pagerAdapter: HomePagerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request data loading for all fragments that will share this ViewModel
        viewModel.loadJsonData()

        observeUIStateForProgressBar()

        binding.toolbar.apply {
            title = "Vexil Machine Round"
        }

        binding.viewpager.adapter = pagerAdapter

        binding.tabLayout.apply {
            tabMode = TabLayout.MODE_SCROLLABLE
        }

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = pagerAdapter.getTitleOfFragment(position)
        }.attach()
    }

    private fun observeUIStateForProgressBar() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.sampleResponseData.collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is ApiResult.Success -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is ApiResult.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
