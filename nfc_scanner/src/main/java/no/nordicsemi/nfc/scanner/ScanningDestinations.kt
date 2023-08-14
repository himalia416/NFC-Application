package no.nordicsemi.nfc.scanner

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.nfc.scanner.views.NfcScanningScreen

val ScanningDestinationId = createSimpleDestination("nfc-scanning-screen")

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId) {
    NfcScanningScreen()
}