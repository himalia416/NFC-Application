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
import no.nordicsemi.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayAlternativeCarrierRecord(
    alternativeCarrierRecord: AlternativeCarrier,
    index: Int
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(
                id = R.string.record_name,
                index + 1,
                alternativeCarrierRecord.recordName
            ),
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            RowInCardView(
                title = stringResource(id = R.string.record_type_name_format),
                description = alternativeCarrierRecord.typeNameFormat
            )
            RowInCardView(
                title = stringResource(id = R.string.record_type),
                description = alternativeCarrierRecord.payloadType
            )
            RowInCardView(
                title = stringResource(id = R.string.record_payload_len),
                stringResource(
                    id = R.string.bytes,
                    alternativeCarrierRecord.payloadLength.toString()
                )
            )
            RowInCardView(
                title = alternativeCarrierRecord.payloadFieldName,
                description = alternativeCarrierRecord.payload
            )
        }
    }
}

@Preview
@Composable
fun DisplayAlternativeCarrierRecordPreview() {
    NordicTheme {
        DisplayAlternativeCarrierRecord(
            alternativeCarrierRecord = AlternativeCarrier(
                payloadLength = 22,
                payload = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}