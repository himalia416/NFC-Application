package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

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
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverCarrier
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RecordTitle
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayHandoverCarrierRecord(
    handoverCarrierRecord: HandoverCarrier,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column(modifier = Modifier.padding(8.dp)) {
        RecordTitle(
            recordTitle = handoverCarrierRecord.recordName,
            index = index,
            recordIcon = Icons.Default.Handshake,
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded }
        )

        AnimatedVisibility(
            visible = isExpanded,
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RowInCardView(
                    title = stringResource(id = R.string.record_type_name_format),
                    description = handoverCarrierRecord.typeNameFormat
                )
                RowInCardView(
                    title = stringResource(id = R.string.record_type),
                    description = handoverCarrierRecord.payloadType
                )
                RowInCardView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        handoverCarrierRecord.payloadLength.toString()
                    )
                )
                RowInCardView(
                    title = handoverCarrierRecord.payloadFieldName,
                    description = handoverCarrierRecord.payload
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplayHandoverCarrierRecordPreview() {
    NordicTheme {
        DisplayHandoverCarrierRecord(
            handoverCarrierRecord = HandoverCarrier(
                payloadLength = 22,
                payload = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}