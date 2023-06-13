package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RecordTitle
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayMimeTypeRecord(
    mimeRecord: MimeRecord,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        RecordTitle(
            recordTitle = mimeRecord.recordName,
            index = index,
            recordIcon = mimeRecord.getRecordIcon(),
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded }
        )
        if (isExpanded) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RowInCardView(
                    title = stringResource(id = R.string.record_type_name_format),
                    description = mimeRecord.typeNameFormat
                )
                RowInCardView(
                    title = stringResource(id = R.string.record_type),
                    description = mimeRecord.payloadType
                )
                RowInCardView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        mimeRecord.payloadLength.toString()
                    )
                )
                RowInCardView(
                    title = mimeRecord.payloadFieldName,
                    description = mimeRecord.payload
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplayMimeTypeRecordPreview() {
    NordicTheme {
        DisplayMimeTypeRecord(
            mimeRecord = MimeRecord(
                typeNameFormat = "Media-type",
                payloadType = "application/vnd.bluetooth.le.oob",
                payloadLength = 89,
                payload = "Nordic_HRM_NFC"
            ),
            index = 2
        )
    }
}
