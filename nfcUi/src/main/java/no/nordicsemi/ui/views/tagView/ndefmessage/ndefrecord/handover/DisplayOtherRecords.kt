package no.nordicsemi.ui.views.tagView.ndefmessage.ndefrecord.handover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
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
import no.nordicsemi.domain.nfcTag.ndef.record.OtherRecords
import no.nordicsemi.nfcui.R
import no.nordicsemi.ui.uicomponents.NfcRowView
import no.nordicsemi.ui.uicomponents.RecordTitleView
import no.nordisemi.utils.toPayloadData

@Composable
fun DisplayOtherRecords(
    record: OtherRecords,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = record.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = Icons.Default.DoneAll,
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
                    description = record.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = record.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        record.payloadLength.toString()
                    )
                )
                NfcRowView(
                    title = record.payloadFieldName,
                    description = record.payload
                )
                record.payloadData?.let {
                    NfcRowView(
                        title = record.payloadFieldName,
                        description = it.value.toPayloadData()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DisplayOtherRecordsPreview() {
    NordicTheme {
        DisplayOtherRecords(
            record = OtherRecords(
                payloadLength = 22,
                payload = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}