package com.example.profile_nfc.views.tagViews.ndefTag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.data.AlternativeCarrier
import com.example.domain.data.AndroidPackage
import com.example.domain.data.HandoverCarrier
import com.example.domain.data.HandoverReceive
import com.example.domain.data.HandoverSelect
import com.example.domain.data.NdefRecord
import com.example.domain.data.OtherExternalType
import com.example.domain.data.SmartPoster
import com.example.domain.data.TextRecord
import com.example.domain.data.URIRecord
import com.example.domain.data.Unknown
import com.example.profile_nfc.R
import com.example.profile_nfc.component.RowInCardView
import com.example.profile_nfc.component.TitleWithIcon
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun RecordView(ndefRecords: List<NdefRecord>) {
    TitleWithIcon(
        icon = painterResource(id = R.drawable.storage_icon),
        title = stringResource(id = R.string.record_info),
        modifier = Modifier.padding(8.dp),
        textStyle = MaterialTheme.typography.titleLarge,
    )

    ndefRecords.forEachIndexed { index, ndefRecord ->
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                when (val recordType = ndefRecord.type) {
                    is TextRecord -> DisplayTextRecord(recordType, index)
                    is URIRecord -> DisplayUriRecord(recordType, index)
                    is AndroidPackage -> DisplayAndroidPackageRecord(recordType, index)
                    is SmartPoster -> DisplaySmartPosterRecord(recordType, index)
                    is AlternativeCarrier -> DisplayAlternativeCarrierRecord(recordType, index)
                    is HandoverCarrier -> DisplayHandoverCarrierRecord(recordType, index)
                    is HandoverReceive -> DisplayHandoverReceiveRecord(recordType, index)
                    is HandoverSelect -> DisplayHandoverSelectRecord(recordType, index)
                    is OtherExternalType -> TODO()
                    Unknown -> TODO()
                    else -> TODO()
                }
            }
        }
    }
}

@Composable
fun DisplayHandoverSelectRecord(
    handoverSelectRecord: HandoverSelect,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, handoverSelectRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = handoverSelectRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = handoverSelectRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                handoverSelectRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = handoverSelectRecord.payloadFieldName,
            secondItem = handoverSelectRecord.payload
        )
    }
}

@Composable
fun DisplayHandoverReceiveRecord(
    handoverReceiveRecord: HandoverReceive,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, handoverReceiveRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = handoverReceiveRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = handoverReceiveRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                handoverReceiveRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = handoverReceiveRecord.payloadFieldName,
            secondItem = handoverReceiveRecord.payload
        )
    }
}

@Composable
fun DisplayHandoverCarrierRecord(
    handoverCarrierRecord: HandoverCarrier,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, handoverCarrierRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = handoverCarrierRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = handoverCarrierRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                handoverCarrierRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = handoverCarrierRecord.payloadFieldName,
            secondItem = handoverCarrierRecord.payload
        )
    }
}

@Composable
fun DisplayAlternativeCarrierRecord(
    alternativeCarrierRecord: AlternativeCarrier,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, alternativeCarrierRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = alternativeCarrierRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = alternativeCarrierRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                alternativeCarrierRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = alternativeCarrierRecord.payloadFieldName,
            secondItem = alternativeCarrierRecord.payload
        )
    }
}

@Composable
fun DisplaySmartPosterRecord(
    smartPosterRecord: SmartPoster,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, smartPosterRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = smartPosterRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = smartPosterRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                smartPosterRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = smartPosterRecord.payloadFieldName,
            secondItem = smartPosterRecord.payload
        )
    }
}

@Composable
private fun DisplayAndroidPackageRecord(
    androidPackageRecord: AndroidPackage,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, androidPackageRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = androidPackageRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = androidPackageRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                androidPackageRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = androidPackageRecord.payloadFieldName,
            secondItem = androidPackageRecord.payload
        )
    }
}

@Composable
private fun DisplayUriRecord(
    uriRecord: URIRecord,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, uriRecord.recordName),
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

@Composable
private fun DisplayTextRecord(
    textRecord: TextRecord,
    index: Int
) {
    Text(
        text = stringResource(id =R.string.record_name,
            index+1, textRecord.recordName),
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
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
fun NdefRecordViewPreview() {
    NordicTheme {
        RecordView(ndefRecords = listOf(NdefRecord()))
    }
}