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
import com.example.domain.data.TextRecordStructure
import com.example.domain.data.SmartPoster
import com.example.domain.data.URIRecordStructure
import com.example.domain.data.Unknown
import com.example.domain.mapper.NdefRecordTypeMapper
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
                when (NdefRecordTypeMapper.getNdefRecordType(
                    ndefRecord.typeNameFormat,
                    ndefRecord.type
                )) {
                    is TextRecordStructure -> DisplayTextRecord(ndefRecord, index)
                    is URIRecordStructure -> DisplayUriRecord(ndefRecord, index)
                    is AndroidPackage -> DisplayAndroidPackageRecord(ndefRecord, index)
                    is SmartPoster -> DisplaySmartPosterRecord(ndefRecord, index)
                    is AlternativeCarrier -> DisplayAlternativeCarrierRecord(ndefRecord, index)
                    is HandoverCarrier -> DisplayHandoverCarrierRecord(ndefRecord, index)
                    is HandoverReceive -> DisplayHandoverReceiveRecord(ndefRecord, index)
                    is HandoverSelect -> DisplayHandoverSelectRecord(ndefRecord, index)
                    is OtherExternalType -> TODO()
                    Unknown -> DisplayAlternativeCarrierRecord(ndefRecord, index)
                }
            }
        }
    }
}

@Composable
fun DisplayHandoverSelectRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getHandoverSelectData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
fun DisplayHandoverReceiveRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getHandoverReceiveData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
fun DisplayHandoverCarrierRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getHandoverCarrierData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
fun DisplayAlternativeCarrierRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getAlternativeCarrierData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
fun DisplaySmartPosterRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getSmartPosterData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
private fun DisplayAndroidPackageRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getAndroidPackageData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = data.payload
        )
    }
}

@Composable
private fun DisplayUriRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getUriData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = "Protocol Field",
            secondItem = data.protocol
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = String(ndefRecord.payloadData!!)
        )
    }
}

@Composable
private fun DisplayTextRecord(ndefRecord: NdefRecord, index: Int) {
    val data = ndefRecord.getTextData(ndefRecord.payloadData!!)
    Text(
        text = "Record ${index + 1}: ${data.recordName}",
        modifier = Modifier.padding(8.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        RowInCardView(
            firstItem = stringResource(id = R.string.record_name_format),
            secondItem = ndefRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = ndefRecord.type
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                ndefRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = "Language code",
            secondItem = data.langCode
        )
        RowInCardView(
            firstItem = "Encoding",
            secondItem = ndefRecord.getTextData(ndefRecord.payloadData!!).encoding
        )
        RowInCardView(
            firstItem = data.payloadFieldName,
            secondItem = ndefRecord.getTextData(ndefRecord.payloadData!!).actualText
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