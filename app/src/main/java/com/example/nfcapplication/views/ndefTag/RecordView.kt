package com.example.nfcapplication.views.ndefTag

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
import com.example.nfcapplication.R
import com.example.nfcapplication.component.RowInCardView
import com.example.nfcapplication.component.TitleWithIcon
import com.example.nfcapplication.data.NdefRecord
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
            Column {
                Text(
                    text = "Record ${index + 1}: Name of the Record",
                    modifier = Modifier.padding(8.dp)
                ) // todo find the name of the Record
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
                        ndefRecord.payloadLength.toString()
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.record_payload_data),
                        secondItem = ndefRecord.payloadData
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NdefRecordViewPreview() {
    NordicTheme {
        RecordView(ndefRecords = listOf(NdefRecord()))
    }
}