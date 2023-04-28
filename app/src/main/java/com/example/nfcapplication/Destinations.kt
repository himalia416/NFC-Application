package com.example.nfcapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profile_nfc.views.Nfc
import com.example.setting.views.SettingsScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import com.example.welcome.views.NfcWelcomeScreen

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