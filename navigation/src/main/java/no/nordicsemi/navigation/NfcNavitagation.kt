package no.nordicsemi.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.ui.NfcUiScreenDestination
import no.nordicsemi.nfcscanner.ScanningDestination
import no.nordicsemi.settings.navigation.NfcSettingsDestination
import no.nordicsemi.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcDestinations = ScanningDestination +
        NfcUiScreenDestination +
        NfcSettingsDestination +
        NfcWelcomeDestination