package no.nordic.ui.views.tagView.ndefmessage.ndefrecord.wellknown

import androidx.compose.animation.AnimatedVisibility
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
import no.nordic.ui.utility.toPayloadData
import no.nordic.ui.uicomponents.NfcRowView
import no.nordic.ui.uicomponents.RecordTitleView
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.nfcui.R

@Composable
fun DisplayMimeTypeRecord(
    mimeRecord: MimeRecord,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = mimeRecord.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = mimeRecord.getRecordIcon(),
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded }
        )
        AnimatedVisibility(
            visible = isExpanded,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NfcRowView(
                    title = stringResource(id = R.string.record_type_name_format),
                    description = mimeRecord.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = mimeRecord.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        mimeRecord.payloadLength.toString()
                    )
                )
                NfcRowView(
                    title = mimeRecord.payloadFieldName,
                    description = mimeRecord.payload
                )
                mimeRecord.payloadData?.let {
                    NfcRowView(
                        title = stringResource(id = R.string.payload_data),
                        description = it.value.toPayloadData()
                    )
                }
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
