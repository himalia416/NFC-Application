package no.nordicsemi.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordic.ui.NfcUiScreenDestination
import no.nordicsemi.nfcscanner.ScanningDestination
import no.nordicsemi.settings.NfcSettingsDestination
import no.nordicsemi.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcDestinations = ScanningDestination +
        NfcUiScreenDestination +
        NfcSettingsDestination +
        NfcWelcomeDestination