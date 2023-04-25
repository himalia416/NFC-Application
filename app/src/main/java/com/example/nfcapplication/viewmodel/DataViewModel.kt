package com.example.nfcapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcapplication.database.ManufacturerNameRepository
import com.example.nfcapplication.database.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class ManufacturerName(
    val identifier: String = "",
    val company: String = "",
    val country: String = "",
)

open class DataViewModel @Inject constructor(
    manufacturerNameRepository: ManufacturerNameRepository
) : ViewModel() {
    private var _manufacturerName = MutableStateFlow<List<ManufacturerName>>(emptyList())
    val manufacturerName = _manufacturerName.asStateFlow()
    private val TAG = "DataViewModel"

    init {
        manufacturerNameRepository.getManufacturerNameOnce()
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> { _manufacturerName.value = resource.data!! }
                    is Resource.Error -> { Log.w(TAG, resource.error!!) }
                }
            }.launchIn(viewModelScope)
    }
}