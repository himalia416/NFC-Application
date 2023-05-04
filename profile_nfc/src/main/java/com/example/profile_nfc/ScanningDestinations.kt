package com.example.profile_nfc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profile_nfc.views.Nfc
import com.example.setting.NfcSettingScreenId
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val ScanningDestinationId = createSimpleDestination("nfc-scanning-screen")

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()
    Nfc { viewModel.navigateTo(NfcSettingScreenId, it) }
}