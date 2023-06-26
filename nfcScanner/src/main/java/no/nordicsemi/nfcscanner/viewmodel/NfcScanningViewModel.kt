package no.nordicsemi.nfcscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import no.nordic.ui.NfcUiDestinationId
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.domain.NfcSettingDestinationArgs
import no.nordicsemi.domain.NfcSettingNoTag
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.nfcscanner.repository.NfcScanningManager
import no.nordicsemi.settings.NfcSettingScreenId
import no.nordicsemi.settingsstorage.domain.NFCSettings
import no.nordicsemi.settingsstorage.repository.SettingsRepository
import no.nordicsemi.welcome.NfcWelcomeDestinationId
import javax.inject.Inject

internal data class NfcViewState(
    val setting: NFCSettings? = null,
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
    var manufacturerName = ""

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
        nfcManager.nfcScanningState.onEach {
            when {
                !it.isScanning -> showScanTag()
                it.tag != null -> tagDiscovered()
            }
        }.launchIn(viewModelScope)
        nfcManager.icManufacturerName.onEach {
            manufacturerName = it
        }.launchIn(viewModelScope)

    }

    private fun tagDiscovered() {
        _state.value = _state.value.copy(state = NfcState.NfcTagDiscovered)
        val discoveredTag = DiscoveredTag(
            serialNumber = nfcManager.serialNumber.value,
            manufacturerName = manufacturerName,
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