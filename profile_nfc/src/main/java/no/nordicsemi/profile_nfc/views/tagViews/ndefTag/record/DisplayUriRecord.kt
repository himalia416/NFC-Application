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
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayUriRecord(
    uriRecord: URIRecord,
    index: Int
) {
    Text(
        text = stringResource(
            id = R.string.record_name,
            index + 1,
            uriRecord.recordName
        ),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = uriRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = uriRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                uriRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.protocol_field),
            secondItem = uriRecord.protocol
        )
        RowInCardView(
            firstItem = uriRecord.payloadFieldName,
            secondItem = uriRecord.actualUri
        )
    }
}

@Preview
@Composable
fun DisplayUriRecordPreview() {
    NordicTheme {
        DisplayUriRecord(
            uriRecord = URIRecord(
                payloadLength = 22,
                protocol = "https://www.",
                actualUri = "nordicsemi.com"
            ),
            index = 2
        )
    }
}