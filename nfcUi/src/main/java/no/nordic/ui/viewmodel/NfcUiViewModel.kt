package no.nordic.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordic.handOverSelectMessageParser.HandOverDataParser
import no.nordic.ui.NfcUiDestinationId
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.settings.navigation.NfcSettingScreenId
import no.nordicsemi.settings.navigation.NfcSettingsWithTag
import java.nio.ByteBuffer
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
internal class NfcUiViewModel @Inject constructor(
    private val handOverMessage: HandOverDataParser,
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

        if (_uiState.value.nfcNdefMessage != null) {
            val ndef = _uiState.value.nfcNdefMessage
            ndef?.ndefRecord?.forEach {
                val r = it.record
                if (r is MimeRecord && r.recordName == "Bluetooth Carrier Configuration LE Record") { // TODO update it to include the HandOverSelect Record type.
                    r.payloadData?.value?.let { payload ->
                        ByteBuffer.wrap(payload)
                        val oobData = handOverMessage.parser(ByteBuffer.wrap(payload))
                        Log.d("AAA", "appearance: ${oobData.appearance} ")
                        Log.d("AAA", "AddressType: ${oobData.bleAddressType} ")
                        Log.d("AAA", "DeviceAddress: ${oobData.bleDeviceAddress} ")
                        Log.d("AAA", "Local Name: ${oobData.localName} ")
                        Log.d("AAA", "role Type: ${oobData.roleType} ")
                        Log.d("AAA", "flags: ")
                        oobData.flags.forEach { flag ->
                            Log.d("AAA", flag)
                        }
                    }
                }
            }
        }
    }

    fun settingsScreenNavigation(discoveredTag: DiscoveredTag) {
        navigator.navigateTo(NfcSettingScreenId, NfcSettingsWithTag(discoveredTag))
    }

    fun onBackNavigation() {
        navigator.navigateUp()
    }
}