package no.nordicsemi.nfcscanner.views

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
import no.nordicsemi.nfcscanner.R
import no.nordicsemi.nfcscanner.viewmodel.NfcScanningViewModel
import no.nordicsemi.nfcscanner.viewmodel.NfcState

@OptIn(ExperimentalMaterial3Api::class)
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

            else ->
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
