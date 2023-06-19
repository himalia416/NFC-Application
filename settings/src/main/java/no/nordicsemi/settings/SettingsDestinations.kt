package no.nordicsemi.settings

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.settings.views.SettingsScreen

val NfcSettingScreenId = createDestination<DiscoveredTag, Unit>("nfc-setting-screen")

@RequiresApi(Build.VERSION_CODES.N)
val NfcSettingsDestination = defineDestination(NfcSettingScreenId) {
    SettingsScreen()
}