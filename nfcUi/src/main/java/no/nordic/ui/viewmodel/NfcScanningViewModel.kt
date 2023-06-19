package no.nordic.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordic.ui.NfcUiDestinationId
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.settings.NfcSettingScreenId
import javax.inject.Inject

@HiltViewModel
internal class NfcScanningViewModel @Inject constructor(
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    val discoveredTag = parameterOf(NfcUiDestinationId)
    private val _uiState: MutableStateFlow<NfcUiState> = MutableStateFlow(NfcUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val nfcTag = discoveredTag.availableTagTechnology
        _uiState.value =
            _uiState.value.copy(
                nfcAInfo = nfcTag.nfcAInfo,
                nfcBInfo = nfcTag.nfcBInfo,
                nfcFInfo = nfcTag.nfcFInfo,
                nfcVInfo = nfcTag.nfcVInfo,
                nfcNdefMessage = nfcTag.nfcNdefMessage,
                mifareClassicMessage = nfcTag.mifareClassicField
            )
    }

    fun settingsScreenNavigation(discoveredTag: DiscoveredTag){
        navigator.navigateTo(NfcSettingScreenId, discoveredTag)
    }

    fun onBackNavigation(){
        navigator.navigateUp()
    }
}