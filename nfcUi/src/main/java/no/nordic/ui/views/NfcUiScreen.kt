package no.nordic.ui.views

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
import no.nordic.ui.viewmodel.NfcUiViewModel
import no.nordic.ui.views.tagView.TagInfoView
import no.nordic.ui.views.tagView.ndefmessage.NdefMessageView
import no.nordic.ui.views.tagView.ndefmessage.ndefrecord.RecordView
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.nfcui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NfcUiScreen() {
    val viewModel: NfcUiViewModel = hiltViewModel()
    val discoveredTag = viewModel.discoveredTag
    val state by viewModel.uiState.collectAsState()

    Column {
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
                    manufacturerName = discoveredTag.manufacturerName,
                    availableTechnologies = discoveredTag.techList,
                    nfcAInfo = state.nfcAInfo,
                    nfcBInfo = state.nfcBInfo,
                    nfcFInfo = state.nfcFInfo,
                    nfcVInfo = state.nfcVInfo
                )
                state.nfcNdefMessage?.let {
                    NdefMessageView(nfcNdefMessage = it)
                    RecordView(ndefRecords = it.ndefRecord)
                }
            }
        }
    }
}
