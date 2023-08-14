package no.nordicsemi.nfc.scanner.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.android.common.permissions.nfc.RequireNfc
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.nfc.scanner.R
import no.nordicsemi.nfc.scanner.viewmodel.NfcScanningViewModel
import no.nordicsemi.nfc.scanner.viewmodel.NfcState

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NfcScanningScreen() {
    val nfcViewModel: NfcScanningViewModel = hiltViewModel()
    val nfcState by nfcViewModel.state.collectAsState()

    if (nfcState.settings?.showWelcomeScreen == true) {
        nfcViewModel.showWelcomeScreen()
    } else {
        RequireNfc {
                Column {
                    NordicAppBar(
                        text = stringResource(id = R.string.app_name),
                        actions = {
                            IconButton(onClick = { nfcViewModel.settingsScreenNavigation() }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Settings,
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                    when (nfcState.state) {
                        NfcState.ScanNfcTag -> ScanNfcTagView()
                        else -> LoadingView()
                    }
                }
        }
    }
}
