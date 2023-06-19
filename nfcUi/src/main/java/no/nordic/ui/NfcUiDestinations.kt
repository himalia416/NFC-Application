package no.nordic.ui

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordic.ui.views.NfcUiScreen
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.domain.nfcTag.DiscoveredTag

val NfcUiDestinationId = createDestination<DiscoveredTag, Unit>("nfc-ui-screen")

@RequiresApi(Build.VERSION_CODES.S)
val NfcUiScreenDestination = defineDestination(NfcUiDestinationId) {
    NfcUiScreen()
}