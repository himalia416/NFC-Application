package no.nordicsemi.nfcscanner

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.nfcscanner.views.Nfc

val ScanningDestinationId = createSimpleDestination("nfc-scanning-screen")

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId) {
    Nfc()
}