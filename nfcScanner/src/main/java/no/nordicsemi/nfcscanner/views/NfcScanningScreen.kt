package no.nordicsemi.nfcscanner.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.android.common.permissions.nfc.RequireNfc
import no.nordicsemi.nfcscanner.viewmodel.NfcScanningViewModel
import no.nordicsemi.nfcscanner.viewmodel.NfcState

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NfcScanningScreen() {
    RequireNfc {
        val nfcViewModel: NfcScanningViewModel = hiltViewModel()
        val nfcState by nfcViewModel.state.collectAsState()

        when (nfcState.setting?.showWelcomeScreen) {
            true -> {
                nfcViewModel.showWelcomeScreen()
            }

            else -> when (nfcState.state) {
                NfcState.ScanNfcTag -> ScanNfcTagView()
                else -> LoadingView()
            }

        }
    }
}
