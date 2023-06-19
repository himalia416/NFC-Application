package no.nordicsemi.nfcscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import no.nordicsemi.nfcscanner.repository.NfcScanningManager
import no.nordicsemi.nfcscanner.repository.NfcScanningState
import no.nordicsemi.welcome.NfcWelcomeDestinationId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.settingsstorage.domain.NFCSettings
import no.nordicsemi.settingsstorage.repository.SettingsRepository
import javax.inject.Inject

internal data class NfcViewState(
    val setting: NFCSettings? = null,
    val state: NfcState = NfcState.ScanNfcTag,
    val nfcScanningState: NfcScanningState = NfcScanningState(),
    val manufacturerName: String = ""
)

@HiltViewModel
internal class NfcViewModel @Inject constructor(
    private val navigator: Navigator,
    private val nfcManager: NfcScanningManager,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()

    init {
        settingsRepository.settings
            .onEach {
                _state.value = _state.value.copy(setting = it)
            }.launchIn(viewModelScope)
        startNfcScanning()
    }

    fun showWelcomeScreen() {
        navigator.navigateTo(NfcWelcomeDestinationId)
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
            .apply {
                icManufacturerName.onEach {
                    manufacturerName(it)
                }
                    .launchIn(viewModelScope)
            }

    }

    private fun manufacturerName(name: String) {
        _state.value = _state.value.copy(manufacturerName = name)
    }

    private fun showNfcNotEnabledPage() {
        _state.value = _state.value.copy(state = NfcState.NfcNotEnabled)
    }

    private fun showNfcNotSupported() {
        _state.value = _state.value.copy(state = NfcState.NfcNotSupported)
    }

    private fun tagDiscovered() {
        _state.value = _state.value.copy(
            state = NfcState.NfcTagDiscovered,
            nfcScanningState = nfcManager.nfcScanningState.value
        )
    }

    fun showTag(discoveredTag: DiscoveredTag) {
        navigator.navigateTo(NfcUiDestinationId, discoveredTag)
    }

    private fun showScanTag() {
        _state.value = _state.value.copy(state = NfcState.ScanNfcTag)
    }

    fun enableNfc() {
        _state.value = _state.value.copy(state = NfcState.EnableNfc)
    }
}