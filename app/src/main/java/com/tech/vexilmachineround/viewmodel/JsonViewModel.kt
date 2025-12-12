package com.tech.vexilmachineround.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.vexilmachineround.model.ResponseObject
import com.tech.vexilmachineround.repository.JsonRepository
import com.tech.vexilmachineround.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JsonViewModel @Inject constructor(private val jsonRepository: JsonRepository) : ViewModel() {

    private val _sampleResponseData = MutableStateFlow<ApiResult<ResponseObject>>(ApiResult.Loading)
    val sampleResponseData: StateFlow<ApiResult<ResponseObject>> = _sampleResponseData.asStateFlow()

    fun loadJsonData() {
        viewModelScope.launch {
            _sampleResponseData.value = ApiResult.Loading
            try {
                val data = jsonRepository.getSampleData()
                _sampleResponseData.value = ApiResult.Success(data)
            } catch (e: Exception) {
                _sampleResponseData.value = ApiResult.Error("Failed to load data: ${e.message}", e)
            }
        }
    }
}