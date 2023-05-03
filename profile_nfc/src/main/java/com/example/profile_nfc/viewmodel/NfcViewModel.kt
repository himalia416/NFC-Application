package com.example.profile_nfc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.NfcTag
import com.example.profile_nfc.repository.NfcScanningManager
import com.example.profile_nfc.repository.NfcScanningState
import com.example.serialization.repository.NfcSerialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class NfcViewState(
    val state: NfcState = ScanNfcTag,
    val nfcScanningState: NfcScanningState = NfcScanningState(),
)

@HiltViewModel
class NfcViewModel @Inject constructor(
    private val nfcManager: NfcScanningManager,
    private val nfcSerialization: NfcSerialization
) : ViewModel() {
    private val _state: MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()

    init {
        startNfcScanning()
    }

    private fun startNfcScanning() {
        nfcManager
            .apply {
                nfcScanningState.onEach {
                    when {
                        !it.isNfcSupported -> showNfcNotSupported()
                        !it.isNfcEnabled -> showNfcNotEnabledPage()
                        it.tag != null -> tagDiscovered()
                        else -> showScanTag()
                    }
                }.launchIn(viewModelScope)
            }
    }

    private fun showNfcNotEnabledPage() {
        _state.value = _state.value.copy(state = NfcNotEnabled)
    }

    private fun showNfcNotSupported() {
        _state.value = _state.value.copy(state = NfcNotSupported)
    }

    private fun tagDiscovered() {
        _state.value = _state.value.copy(
            state = NfcTagDiscovered,
            nfcScanningState = nfcManager.nfcScanningState.value
        )
        nfcManager.nfcScanningState.value.tag?.let { serializationOfNfcTag(it) }
    }

    fun showScanTag() {
        _state.value = _state.value.copy(state = ScanNfcTag)
    }

    fun enableNfc() {
        _state.value = _state.value.copy(state = EnableNfc)
    }

    private fun serializationOfNfcTag(nfcTag: NfcTag){
        Log.d("TAG", "serializationOfNfcTag: ${nfcSerialization.toJson(nfcTag)}")
    }
}