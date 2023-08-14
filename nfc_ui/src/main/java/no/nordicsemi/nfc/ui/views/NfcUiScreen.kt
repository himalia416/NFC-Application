package no.nordicsemi.nfc.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
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
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.nfc.domain.nfcTag.DiscoveredTag
import no.nordicsemi.nfc.domain.nfcTag.NfcTag
import no.nordicsemi.nfc.ui.R
import no.nordicsemi.nfc.ui.viewmodel.NfcUiViewModel
import no.nordicsemi.nfc.ui.views.tagView.TagInfoView
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.NdefMessageView
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.RecordView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NfcUiScreen() {
    val viewModel: NfcUiViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val nfcTag = state.availableTagTechnology

    Column {
        val discoveredTag = DiscoveredTag(
            serialNumber = state.serialNumber,
            manufacturerName = state.manufacturerName,
            techList = state.techList,
            availableTagTechnology = NfcTag(
                nfcNdefMessage = nfcTag?.nfcNdefMessage,
                mifareClassicField = nfcTag?.mifareClassicMessage,
                nfcAInfo = nfcTag?.nfcAInfo,
                nfcBInfo = nfcTag?.nfcBInfo,
                nfcFInfo = nfcTag?.nfcFInfo,
                nfcVInfo = nfcTag?.nfcVInfo,
            )
        )

        NordicAppBar(
            text = stringResource(id = R.string.ndef_tag),
            onNavigationButtonClick = { viewModel.onBackNavigation() },
            actions = {
                IconButton(onClick = { viewModel.settingsScreenNavigation(discoveredTag) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                    )
                }
            }
        )
        LazyColumn {
            item {
                TagInfoView(
                    id = discoveredTag.serialNumber,
                    manufacturerName = state.manufacturerName,
                    techList = discoveredTag.techList,
                    nfcAInfo = nfcTag?.nfcAInfo,
                    nfcBInfo = nfcTag?.nfcBInfo,
                    nfcFInfo = nfcTag?.nfcFInfo,
                    nfcVInfo = nfcTag?.nfcVInfo,
                )
                nfcTag?.nfcNdefMessage?.let { ndefMessage ->
                    NdefMessageView(nfcNdefMessage = ndefMessage)
                    RecordView(ndefRecords = ndefMessage.ndefRecord) { viewModel.onBleConnection(it) }
                }
            }
        }
    }
}
