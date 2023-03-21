package com.example.nfcapplication.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.nfcapplication.repository.NfcManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class NfcViewState(
    val state: NfcState = ShowWelcomeDialog(false),
)

@SuppressLint("StaticFieldLeak")
@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class NfcViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val nfcManager: NfcManager
) : ViewModel() {
    private val _state : MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()

    fun showScanningPage(){
        _state.value = _state.value.copy(
            state = ShowTagDiscoveredPage,
        )
    }

    fun showTagDiscoveredPage(){
        _state.value = _state.value.copy(
            state = ShowTagDiscoveredPage,
        )
    }

    fun showHomePage() {
        _state.value = _state.value.copy(
            state = ShowTagDiscoveredPage,
        )
    }

    fun showNfcNotEnabledPage() {
        _state.value = _state.value.copy(
            state = ShowTagDiscoveredPage,
        )
    }

    fun showNfcNotSupportedPage() {
        _state.value = _state.value.copy(
            state = ShowTagDiscoveredPage,
        )
    }
}