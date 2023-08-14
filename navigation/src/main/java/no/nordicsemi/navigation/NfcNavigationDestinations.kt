package no.nordicsemi.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.nfcBleconnection.NfcBleDeviceScreenDestination
import no.nordicsemi.nfcscanner.ScanningDestination
import no.nordicsemi.settings.navigation.NfcSettingsDestination
import no.nordicsemi.ui.NfcUiScreenDestination
import no.nordicsemi.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcNavigationDestinations = ScanningDestination +
        NfcUiScreenDestination +
        NfcBleDeviceScreenDestination +
        NfcSettingsDestination +
        NfcWelcomeDestination