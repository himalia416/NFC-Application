package com.example.setting

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.data.NfcTag
import com.example.setting.views.SettingsScreen
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination

val NfcSettingScreenId = createDestination<NfcTag, Unit>("nfc-setting-screen")

@RequiresApi(Build.VERSION_CODES.N)
val NfcSettingsDestination = defineDestination(NfcSettingScreenId) {
    SettingsScreen()
}