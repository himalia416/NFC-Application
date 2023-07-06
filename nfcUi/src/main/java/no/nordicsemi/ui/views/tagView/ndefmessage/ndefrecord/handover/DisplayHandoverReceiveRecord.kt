package no.nordicsemi.ui.views.tagView.ndefmessage.ndefrecord.handover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.ui.uicomponents.NfcRowView
import no.nordicsemi.ui.uicomponents.RecordTitleView
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.nfcui.R

@Composable
fun DisplayHandoverReceiveRecord(
    handoverReceiveRecord: HandoverReceive,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = handoverReceiveRecord.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = Icons.Default.Handshake,
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
                    description = handoverReceiveRecord.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = handoverReceiveRecord.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        handoverReceiveRecord.payloadLength.toString()
                    )
                )
                NfcRowView(
                    title = handoverReceiveRecord.payloadFieldName,
                    description = handoverReceiveRecord.payload
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplayHandoverReceiveRecordPreview() {
    NordicTheme {
        DisplayHandoverReceiveRecord(
            handoverReceiveRecord = HandoverReceive(
                payloadLength = 22,
                payload = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}