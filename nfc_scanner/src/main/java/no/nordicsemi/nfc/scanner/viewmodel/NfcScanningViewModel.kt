package no.nordicsemi.nfc.scanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.nfc.domain.nfcTag.DiscoveredTag
import no.nordicsemi.nfc.scanner.repository.NfcScanningManager
import no.nordicsemi.nfc.settings.navigation.NfcSettingDestinationArgs
import no.nordicsemi.nfc.settings.navigation.NfcSettingNoTag
import no.nordicsemi.nfc.settings.navigation.NfcSettingScreenId
import no.nordicsemi.nfc.settingsstorage.domain.NFCSettings
import no.nordicsemi.nfc.settingsstorage.repository.SettingsRepository
import no.nordicsemi.nfc.ui.NfcUiDestinationId
import no.nordicsemi.nfc.welcome.NfcWelcomeDestinationId
import javax.inject.Inject

internal data class NfcViewState(
    val settings: NFCSettings? = null,
    val state: NfcState = NfcState.ScanNfcTag,
)

@HiltViewModel
internal class NfcScanningViewModel @Inject constructor(
    private val navigator: Navigator,
    private val nfcManager: NfcScanningManager,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<NfcViewState> = MutableStateFlow(NfcViewState())
    val state = _state.asStateFlow()

    init {
        settingsRepository.settings
            .onEach {
                _state.value = _state.value.copy(settings = it)
            }.launchIn(viewModelScope)
        startNfcScanning()
    }

    fun showWelcomeScreen() {
        navigator.navigateTo(NfcWelcomeDestinationId)
    }

    private fun startNfcScanning() {
        nfcManager.nfcScanningState.onEach {
            when {
                !it.isScanning -> showScanTag()
                it.tag != null -> tagDiscovered()
            }
        }.launchIn(viewModelScope)
    }

    private fun tagDiscovered() {
        _state.value = _state.value.copy(state = NfcState.NfcTagDiscovered)
        val discoveredTag = DiscoveredTag(
            serialNumber = nfcManager.serialNumber.value,
            manufacturerName = "",
            techList = nfcManager.techList.value,
            availableTagTechnology = nfcManager.nfcScanningState.value.tag!!
        )
        navigator.navigateTo(NfcUiDestinationId, discoveredTag)
        _state.value = NfcViewState()
    }

    private fun showScanTag() {
        _state.value = _state.value.copy(state = NfcState.ScanNfcTag)
    }

    fun settingsScreenNavigation() {
        val args: NfcSettingDestinationArgs = NfcSettingNoTag
        navigator.navigateTo(NfcSettingScreenId, args)
    }
}