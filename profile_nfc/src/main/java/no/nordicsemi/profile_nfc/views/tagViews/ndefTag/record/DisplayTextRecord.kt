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
import no.nordicsemi.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayTextRecord(
    textRecord: TextRecord,
    index: Int
) {
    Text(
        text = stringResource(
            id = R.string.record_name,
            index + 1,
            textRecord.recordName
        ),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type_name_format),
            secondItem = textRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = textRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                textRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.language_code),
            secondItem = textRecord.langCode
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.encoding),
            secondItem = textRecord.encoding
        )
        RowInCardView(
            firstItem = textRecord.payloadFieldName,
            secondItem = textRecord.actualText
        )
    }
}

@Preview
@Composable
fun DisplayTextRecordPreview() {
    NordicTheme {
        DisplayTextRecord(
            textRecord = TextRecord(
                payloadLength = 22,
                langCode = "en",
                encoding = "UTF-8",
                actualText = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}