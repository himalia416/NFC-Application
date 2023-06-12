package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplaySmartPosterRecord(
    smartPosterRecord: SmartPoster,
    index: Int
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(
                id = R.string.record_name,
                index + 1,
                smartPosterRecord.recordName
            ),
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            smartPosterRecord.textRecord?.let {
                DisplayTextRecord(textRecord = it, index = index)
            }
            DisplayUriRecord(uriRecord = smartPosterRecord.uriRecord, index = index)
            smartPosterRecord.actionRecord?.let {
                RowInCardView(
                    title = "Action type",
                    description = it.actionType
                )
                RowInCardView(
                    title = "Action Data",
                    description = it.actionData
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplaySmartPosterRecordPreview() {
    NordicTheme {
        DisplaySmartPosterRecord(
            smartPosterRecord = SmartPoster(
                textRecord = TextRecord(
                    payloadLength = 22,
                    langCode = "en",
                    encoding = "UTF-8",
                    actualText = "NordicSemiconductor ASA"
                ),
                uriRecord = URIRecord(
                    typeNameFormat = "NFC Forum well-known type",
                    payloadLength = 22,
                    protocol = "https://www.",
                    uri = "nordicsemi.com",
                    actualUri = "https://www.nordicsemi.com"
                )
            ),
            index = 2
        )
    }
}