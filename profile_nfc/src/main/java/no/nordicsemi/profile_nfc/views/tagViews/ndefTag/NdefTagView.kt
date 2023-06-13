package no.nordicsemi.profile_nfc.views.tagViews.ndefTag

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.profile_nfc.views.tagViews.TagInfoView

@Composable
fun NdefTagView(
    generalTagInfo: GeneralTagInformation,
    nfcNdefMessage: NfcNdefMessage,
    manufacturerName: String,
) {
    LazyColumn {
        item {
            TagInfoView(
                generalTagInfo = generalTagInfo,
                manufacturerName = manufacturerName
            )
            NdefMessageView(nfcNdefMessage = nfcNdefMessage)
            RecordView(ndefRecords = nfcNdefMessage.ndefRecord)
        }
    }
}