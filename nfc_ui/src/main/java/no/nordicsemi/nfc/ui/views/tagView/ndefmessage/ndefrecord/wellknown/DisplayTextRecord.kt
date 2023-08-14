package no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.wellknown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.nfc.ui.uicomponents.NfcRowView
import no.nordicsemi.nfc.ui.uicomponents.RecordTitleView
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.nfc.ui.R
import no.nordicsemi.nfc.utils.DataByteArray
import no.nordicsemi.nfc.utils.toPayloadData

@Composable
fun DisplayTextRecord(
    textRecord: TextRecord,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = textRecord.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = Icons.Default.TextFormat,
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
                    description = textRecord.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = textRecord.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        textRecord.payloadLength.toString()
                    )
                )
                NfcRowView(
                    title = stringResource(id = R.string.language_code),
                    description = textRecord.langCode
                )
                NfcRowView(
                    title = stringResource(id = R.string.encoding),
                    description = textRecord.encoding
                )
                NfcRowView(
                    title = textRecord.payloadFieldName,
                    description = textRecord.actualText
                )
                textRecord.payloadData?.let {
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
fun DisplayTextRecordPreview() {
    NordicTheme {
        DisplayTextRecord(
            textRecord = TextRecord(
                payloadLength = 22,
                langCode = "en",
                encoding = "UTF-8",
                actualText = "NordicSemiconductor ASA",
                payloadData = DataByteArray("NordicSemiconductor ASA".toByteArray())
            ),
            index = 2
        )
    }
}