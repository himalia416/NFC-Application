package no.nordicsemi.nfcscanner.views

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.nfcscanner.viewmodel.NfcState
import no.nordicsemi.nfcscanner.viewmodel.NfcViewModel
import no.nordicsemi.nfcscanner.views.nfcStateViews.EnableNfcView
import no.nordicsemi.nfcscanner.views.nfcStateViews.LoadingView
import no.nordicsemi.nfcscanner.views.nfcStateViews.NfcNotEnabledView
import no.nordicsemi.nfcscanner.views.nfcStateViews.NfcNotSupportedView
import no.nordicsemi.nfcscanner.views.nfcStateViews.ScanNfcTagView

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Nfc() {
    val context = LocalContext.current
    val nfcViewModel: NfcViewModel = hiltViewModel()
    val nfcState by nfcViewModel.state.collectAsState()
    val serialNumber by nfcViewModel.serialNumber.collectAsState()
    val techList by nfcViewModel.techList.collectAsState()

    val startNfcSettingsActivityCallback by remember {
        derivedStateOf {
            { context.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
        }
    }
    when (nfcState.setting?.showWelcomeScreen) {
        true -> { nfcViewModel.showWelcomeScreen() }
        false ->
            when (nfcState.state) {
                NfcState.NfcNotSupported -> NfcNotSupportedView()
                NfcState.NfcNotEnabled -> EnableNfcView(
                    onSettingClicked = { startNfcSettingsActivityCallback() },
                    onCancelClicked = { nfcViewModel.enableNfc() }
                )

                NfcState.EnableNfc -> NfcNotEnabledView()
                NfcState.ScanNfcTag -> ScanNfcTagView()
                NfcState.NfcTagDiscovered -> {
                    val discoveredTag = DiscoveredTag(
                        serialNumber = serialNumber,
                        techList = techList,
                        manufacturerName = nfcState.manufacturerName,
                        availableTagTechnology = nfcState.nfcScanningState.tag!!
                    )
                    nfcViewModel.showTag(discoveredTag)
                }

            }

        else -> LoadingView()
    }
}