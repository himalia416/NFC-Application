package com.example.setting

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.setting.views.SettingsScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val NfcSettingScreenId = createSimpleDestination("nfc-setting-screen")

val NfcSettingsDestination = defineDestination(NfcSettingScreenId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()

    SettingsScreen(onBackNavigation = { viewModel.navigateUp() })
}