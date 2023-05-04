package com.example.setting.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.serialization.domain.NfcJsonAdapter
import com.example.setting.NfcSettingScreenId
import com.example.setting.domain.NFCSettings
import com.example.setting.repository.SettingsRepository
import com.example.setting.views.NavigateUp
import com.example.setting.views.OnAboutNfcClick
import com.example.setting.views.OnEmailClick
import com.example.setting.views.OnExportScanResultClick
import com.example.setting.views.OnHelpClick
import com.example.setting.views.OnImportScanClick
import com.example.setting.views.OnPlaySoundClick
import com.example.setting.views.OnScanHistoryClick
import com.example.setting.views.OnVibrateClick
import com.example.setting.views.SettingsScreenViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import javax.inject.Inject

private val NFC_INFOCENTER_LINK =
    Uri.parse("https://infocenter.nordicsemi.com/topic/sdk_nrf5_v17.1.0/examples_nfc.html")

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    repository: SettingsRepository,
    navigator: Navigator,
    savedStateHandle: SavedStateHandle,
    private val jsonAdapter: NfcJsonAdapter,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    val state = repository.settings.stateIn(viewModelScope, SharingStarted.Eagerly, NFCSettings())

    private val nfcTag = parameterOf(NfcSettingScreenId)

    fun onEvent(event: SettingsScreenViewEvent) {
        when (event) {
            is OnImportScanClick -> importScanResult()
            is OnExportScanResultClick -> exportScanResult()
            is OnScanHistoryClick -> showScanHistory()
            is OnEmailClick -> shareScanResultInEmail()
            is OnPlaySoundClick -> onPlaySoundClick()
            is OnVibrateClick -> onVibrationClick()
            is OnAboutNfcClick -> open(NFC_INFOCENTER_LINK)
            is OnHelpClick -> showWelcomeScreen()
            is NavigateUp -> navigateUp()
        }
    }

    private fun showWelcomeScreen() {
        TODO("Not yet implemented")
    }

    private fun onVibrationClick() {
        TODO("Not yet implemented")
    }

    private fun onPlaySoundClick() {
        TODO("Not yet implemented")
    }

    private fun shareScanResultInEmail() {
        TODO("Not yet implemented")
    }

    private fun showScanHistory() {
        TODO("Not yet implemented")
    }

    private fun exportScanResult() {
        Log.d("TAG", "importScanResult: ${jsonAdapter.toJson(nfcTag)}")
    }

    private fun importScanResult() {
        TODO("Not yet implemented")
    }

}