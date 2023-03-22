package com.example.nfcapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.nfcapplication.repository.NfcScanningManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class NfcViewState(
    val state: NfcState = ShowWelcomeDialog(false),
    val serialNumber: MutableStateFlow<String> = MutableStateFlow(""),
    val isNfcSupported: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val isNfcEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false),
)

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class NfcViewModel @Inject constructor(
    private val nfcManager: NfcScanningManager
) : ViewModel() {
    private val _state: MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()
    val serialNumber = nfcManager.serialNumber

    fun showHomePage() {
        _state.value = _state.value.copy(
            state = ScanNfcTag,
            serialNumber = nfcManager.serialNumber,
            isNfcSupported = nfcManager.isNfcSupported,
            isNfcEnabled = nfcManager.isNfcEnabled
        )
    }

    fun showNfcNotEnabledPage() {
        _state.value = _state.value.copy(
            state = NfcNotEnabled,
        )
    }
}