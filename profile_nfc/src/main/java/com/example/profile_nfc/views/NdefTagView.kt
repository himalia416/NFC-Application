package com.example.profile_nfc.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.profile_nfc.data.GeneralTagInformation
import com.example.profile_nfc.data.NfcNdefMessage
import com.example.profile_nfc.views.tagViews.ndefTag.DataInfoView
import com.example.profile_nfc.views.tagViews.ndefTag.RecordView
import com.example.profile_nfc.views.tagViews.ndefTag.TagInfoView

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