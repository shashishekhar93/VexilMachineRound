package com.tech.vexilmachineround.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tech.vexilmachineround.repository.JsonRepository

@Suppress("UNCHECKED_CAST")
class JsonViewModelFactory(private val jsonRepository: JsonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JsonViewModel(jsonRepository) as T
    }
}