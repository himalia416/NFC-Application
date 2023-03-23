package com.example.nfcapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcapplication.repository.NfcScanningManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class NfcViewState(
    val state: NfcState = NfcScanTag,
    val serialNumber: StateFlow<String> = MutableStateFlow(""),
    val availableTechnology: StateFlow<List<String>> = MutableStateFlow(emptyList()),
)

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class NfcViewModel @Inject constructor(
    private val nfcManager: NfcScanningManager
) : ViewModel() {
    private val _state: MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()

    init {
        startNfcScanning()
    }

    private fun startNfcScanning() {
        nfcManager
            .apply {
                serialNumber.onEach {
                    if (it.isNotEmpty()) {
                        tagDiscovered()
                    } else showScanTag()
                }.launchIn(viewModelScope)
                isNfcEnabled.onEach {
                    if (it) {
                        showScanTag()
                    } else showNfcNotEnabledPage()
                }.launchIn(viewModelScope)
                isNfcSupported.onEach {
                    if (!it) showNfcNotSupported()
                }.launchIn(viewModelScope)
            }
    }

    private fun showNfcNotEnabledPage() {
        _state.value = _state.value.copy(
            state = NfcNotEnabled,
        )
    }

    private fun showNfcNotSupported() {
        _state.value = _state.value.copy(
            state = NfcNotSupported,
        )
    }

    private fun tagDiscovered() {
        _state.value = _state.value.copy(
            state = NfcTagDiscovered,
            serialNumber = nfcManager.serialNumber,
            availableTechnology = nfcManager.tagTechnology
        )
    }

    private fun showScanTag() {
        _state.value = _state.value.copy(
            state = NfcScanTag,
        )
    }

    fun enableNfc() {
        _state.value = _state.value.copy(
            state = EnableNfc,
        )
    }
}