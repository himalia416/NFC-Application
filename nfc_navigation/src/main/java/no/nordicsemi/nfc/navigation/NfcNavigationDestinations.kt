package no.nordicsemi.nfc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.nfc.ble.NfcBleDeviceScreenDestination
import no.nordicsemi.nfc.scanner.ScanningDestination
import no.nordicsemi.nfc.settings.navigation.NfcSettingsDestination
import no.nordicsemi.nfc.ui.NfcUiScreenDestination
import no.nordicsemi.nfc.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcNavigationDestinations = ScanningDestination +
        NfcUiScreenDestination +
        NfcBleDeviceScreenDestination +
        NfcSettingsDestination +
        NfcWelcomeDestination