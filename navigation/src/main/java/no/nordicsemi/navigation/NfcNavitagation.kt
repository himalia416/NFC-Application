package no.nordicsemi.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.profile_nfc.ScanningDestination
import no.nordicsemi.setting.NfcSettingsDestination
import no.nordicsemi.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcDestinations = ScanningDestination + NfcSettingsDestination + NfcWelcomeDestination