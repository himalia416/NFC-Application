package com.example.nfcapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.example.nfcapplication.R
import com.example.nfcapplication.data.GeneralTagInformation
import com.example.nfcapplication.data.NfcNdefMessage
import com.example.nfcapplication.views.ndefTag.DataInfoView
import com.example.nfcapplication.views.ndefTag.RecordView
import com.example.nfcapplication.views.ndefTag.TagInfoView
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NdefTagView(
    generalTagInfo: GeneralTagInformation,
    nfcNdefMessage: NfcNdefMessage,
    onBackButtonClicked: () -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column {
        NordicAppBar(
            text = stringResource(id = R.string.ndef_tag),
            onNavigationButtonClick = onBackButtonClicked
        )
        LazyColumn {
            item {
                TagInfoView(generalTagInfo)
                DataInfoView(nfcNdefMessage, isExpanded) { isExpanded = !isExpanded }
                if (isExpanded) {
                    RecordView(ndefRecords = nfcNdefMessage.ndefRecord)
                }

            }
        }
    }
}