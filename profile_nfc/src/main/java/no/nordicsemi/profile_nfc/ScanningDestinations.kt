package no.nordicsemi.profile_nfc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.profile_nfc.views.Nfc
import no.nordicsemi.setting.NfcSettingScreenId
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel

val ScanningDestinationId = createSimpleDestination("nfc-scanning-screen")

@RequiresApi(Build.VERSION_CODES.S)
val ScanningDestination = defineDestination(ScanningDestinationId) {
    val viewModel: SimpleNavigationViewModel = hiltViewModel()
    Nfc { viewModel.navigateTo(NfcSettingScreenId, it) }
}