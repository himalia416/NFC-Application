package no.nordicsemi.setting

import android.os.Build
import androidx.annotation.RequiresApi
import no.nordicsemi.domain.nfcTag.NfcTag
import no.nordicsemi.setting.views.SettingsScreen
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination

val NfcSettingScreenId = createDestination<NfcTag, Unit>("nfc-setting-screen")

@RequiresApi(Build.VERSION_CODES.N)
val NfcSettingsDestination = defineDestination(NfcSettingScreenId) {
    SettingsScreen()
}