package com.example.nfcapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.settings.views.SettingsScreen
import com.example.nfcapplication.views.Nfc
import com.example.nfcapplication.views.NfcWelcomeScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val NfcWelcomeDestinationId = createSimpleDestination("nfc-welcome-screen")
val ScanningDestinationId = createSimpleDestination("nfc-scanning-screen")
val NfcSettingScreenId = createSimpleDestination("nfc-setting-screen")

val NfcWelcomeScreen = defineDestination(NfcWelcomeDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    NfcWelcomeScreen(onScanningScreenNavigation = { viewModel.navigateTo(ScanningDestinationId) })
}

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()
    Nfc { viewModel.navigateTo(NfcSettingScreenId) }

}

val NfcSettingDestination = defineDestination(NfcSettingScreenId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    SettingsScreen(onBackNavigation = { viewModel.navigateTo(ScanningDestinationId) })
}