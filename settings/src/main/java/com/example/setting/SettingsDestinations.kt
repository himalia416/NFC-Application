package com.example.setting

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.data.NfcTag
import com.example.setting.views.SettingsScreen
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val NfcSettingScreenId = createDestination<NfcTag, Unit>("nfc-setting-screen")

val NfcSettingsDestination = defineDestination(NfcSettingScreenId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    SettingsScreen(onBackNavigation = { viewModel.navigateUp() })
}