package com.example.nfcapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.views.NfcWelcomeScreen
import com.example.nfcapplication.views.NfcScanningScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val NfcWelcomeDestinationId = createSimpleDestination("nfc-welcome-screen")
val ScanningDestinationId = createSimpleDestination("nfc-scanning")

val NfcWelcomeScreen = defineDestination(NfcWelcomeDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    NfcWelcomeScreen( onScanningScreenNavigation =  { viewModel.navigateTo(ScanningDestinationId) } )
}

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId){
    val viewModel: SimpleNavigationViewModel = hiltViewModel()
    NfcScanningScreen(onBackNavigation = {viewModel.navigateUp()})
}