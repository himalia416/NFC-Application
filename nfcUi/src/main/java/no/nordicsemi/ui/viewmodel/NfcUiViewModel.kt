package no.nordicsemi.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import no.nordicsemi.bleconnection.BleDevice
import no.nordicsemi.bleconnection.NfcBleDeviceDestinationId
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.remotedatabase.domain.ManufacturerNameRepository
import no.nordicsemi.settings.navigation.NfcSettingScreenId
import no.nordicsemi.settings.navigation.NfcSettingsWithTag
import no.nordicsemi.ui.NfcUiDestinationId
import javax.inject.Inject

@HiltViewModel
internal class NfcUiViewModel @Inject constructor(
    private val manufacturerNameRepository: ManufacturerNameRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    private val discoveredTag = parameterOf(NfcUiDestinationId)
    private val _uiState: MutableStateFlow<NfcUiState> = MutableStateFlow(NfcUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val nfcTag = discoveredTag.availableTagTechnology
        _uiState.value = _uiState.value.copy(
            serialNumber = discoveredTag.serialNumber,
            techList = discoveredTag.techList,
            availableTagTechnology = NfcTagState(
                nfcAInfo = nfcTag.nfcAInfo,
                nfcBInfo = nfcTag.nfcBInfo,
                nfcFInfo = nfcTag.nfcFInfo,
                nfcVInfo = nfcTag.nfcVInfo,
                nfcNdefMessage = nfcTag.nfcNdefMessage,
                mifareClassicMessage = nfcTag.mifareClassicField
            )
        )
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(manufacturerName = getIcManufacturerName(getIdentifier()))
        }
    }

    /**
     * Returns first two characters of the serial number as an identifier.
     */
    private fun getIdentifier() = discoveredTag.serialNumber.subSequence(0, 2).toString()

    /**
     * Returns Ic Manufacturer Name.
     */
    private suspend fun getIcManufacturerName(identifier: String): String {
        return manufacturerNameRepository.getManufacturerName(identifier)?.company
            ?: "Company not found"
    }

    /**
     * Navigates to the Settings screen.
     */
    fun settingsScreenNavigation(discoveredTag: DiscoveredTag) {
        navigator.navigateTo(NfcSettingScreenId, NfcSettingsWithTag(discoveredTag))
    }

    /**
     * Back Navigation.
     */
    fun onBackNavigation() {
        navigator.navigateUp()
    }

    fun onBleConnection(device: BleDevice){
        navigator.navigateTo(NfcBleDeviceDestinationId, device)
    }
}