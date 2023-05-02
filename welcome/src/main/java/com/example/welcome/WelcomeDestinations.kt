package com.example.welcome

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profile_nfc.ScanningDestinationId
import com.example.welcome.views.NfcWelcomeScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val NfcWelcomeDestinationId = createSimpleDestination("nfc-welcome-screen")

val NfcWelcomeDestination = defineDestination(NfcWelcomeDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    NfcWelcomeScreen(onScanningScreenNavigation = { viewModel.navigateTo(ScanningDestinationId) })
}