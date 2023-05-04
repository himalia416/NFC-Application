package com.example.profile_nfc.views

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.data.MifareClassicTag
import com.example.domain.data.NdefTag
import com.example.domain.data.NfcTag
import com.example.profile_nfc.R
import com.example.profile_nfc.viewmodel.EnableNfc
import com.example.profile_nfc.viewmodel.NfcNotEnabled
import com.example.profile_nfc.viewmodel.NfcNotSupported
import com.example.profile_nfc.viewmodel.NfcTagDiscovered
import com.example.profile_nfc.viewmodel.NfcViewModel
import com.example.profile_nfc.viewmodel.ScanNfcTag
import com.example.profile_nfc.views.tagViews.otherTags.OtherTagView
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Nfc(onSettingScreenNavigation: (NfcTag) -> Unit) {
    val context = LocalContext.current
    val nfcViewModel: NfcViewModel = hiltViewModel()
    val nfcState by nfcViewModel.state.collectAsState()

    when (nfcState.state) {
        NfcNotSupported -> NfcNotSupportedView()
        NfcNotEnabled -> EnableNfcView(
            onSettingClicked = { context.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) },
            onCancelClicked = { nfcViewModel.enableNfc() }
        )

        EnableNfc -> NfcNotEnableView()
        ScanNfcTag -> ScanNfcTagView()
        NfcTagDiscovered ->
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
                    is MifareClassicTag -> MifareClassicTagView(generalTagInfo = tag.general)

                    is NdefTag -> NdefTagView(
                        generalTagInfo = tag.general,
                        nfcNdefMessage = tag.nfcNdefMessage
                    )

                    null -> LoadingView()
                    else -> OtherTagView(generalTagInfo = tag.general)
                }
            }
    }
}