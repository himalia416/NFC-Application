package no.nordicsemi.profile_nfc.views

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.domain.nfcTag.MifareClassicTag
import no.nordicsemi.domain.nfcTag.NdefTag
import no.nordicsemi.domain.nfcTag.NfcTag
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.viewmodel.NfcState
import no.nordicsemi.profile_nfc.viewmodel.NfcViewModel
import no.nordicsemi.profile_nfc.views.tagViews.otherTags.OtherTagView

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Nfc(onSettingScreenNavigation: (NfcTag) -> Unit) {
    val context = LocalContext.current
    val nfcViewModel: NfcViewModel = hiltViewModel()
    val nfcState by nfcViewModel.state.collectAsState()

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
                    onCancelClicked = { nfcViewModel.enableNfc() })

                NfcState.EnableNfc -> NfcNotEnabledView()
                NfcState.ScanNfcTag -> ScanNfcTagView()
                NfcState.NfcTagDiscovered ->
                    Column {
                        NordicAppBar(
                            text = stringResource(id = R.string.ndef_tag),
                            onNavigationButtonClick = { nfcViewModel.showScanTag() },
                            actions = {
                                IconButton(onClick = { onSettingScreenNavigation(nfcState.nfcScanningState.tag!!) }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = null,
                                    )
                                }
                            }
                        )
                        when (val tag = nfcState.nfcScanningState.tag) {
                            is MifareClassicTag -> MifareClassicTagView(
                                generalTagInfo = tag.general,
                                manufacturerName = nfcState.manufacturerName
                            )

                            is NdefTag -> NdefTagView(
                                generalTagInfo = tag.general,
                                nfcNdefMessage = tag.nfcNdefMessage,
                                manufacturerName = nfcState.manufacturerName
                            )

                            null -> LoadingView()
                            else -> OtherTagView(
                                generalTagInfo = tag.general,
                                manufacturerName = nfcState.manufacturerName
                            )
                        }
                    }
            }

        null -> LoadingView()
    }
}