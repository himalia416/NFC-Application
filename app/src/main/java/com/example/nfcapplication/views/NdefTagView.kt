package com.example.nfcapplication.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.nfcapplication.data.GeneralTagInformation
import com.example.nfcapplication.data.NfcNdefMessage
import com.example.nfcapplication.views.tagViews.ndefTag.DataInfoView
import com.example.nfcapplication.views.tagViews.ndefTag.RecordView
import com.example.nfcapplication.views.tagViews.ndefTag.TagInfoView

@Composable
fun NdefTagView(
    generalTagInfo: GeneralTagInformation,
    nfcNdefMessage: NfcNdefMessage,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    LazyColumn {
        item {
            TagInfoView(generalTagInfo = generalTagInfo)
            DataInfoView(
                nfcNdefMessage = nfcNdefMessage,
                isShowRecordsExpanded = isExpanded,
                onShowRecordClicked = { isExpanded = !isExpanded })
            if (isExpanded) {
                RecordView(ndefRecords = nfcNdefMessage.ndefRecord)
            }

        }
    }
}