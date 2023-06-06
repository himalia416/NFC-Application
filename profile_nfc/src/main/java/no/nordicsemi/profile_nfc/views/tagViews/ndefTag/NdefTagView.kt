package no.nordicsemi.profile_nfc.views.tagViews.ndefTag

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.profile_nfc.views.tagViews.TagInfoView

@Composable
fun NdefTagView(
    generalTagInfo: GeneralTagInformation,
    nfcNdefMessage: NfcNdefMessage,
    manufacturerName: String,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    LazyColumn {
        item {
            TagInfoView(
                generalTagInfo = generalTagInfo,
                manufacturerName = manufacturerName
            )
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