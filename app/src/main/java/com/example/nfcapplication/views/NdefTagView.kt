package com.example.nfcapplication.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfcapplication.R
import com.example.nfcapplication.data.GeneralTagInformation
import com.example.nfcapplication.data.NdefRecord
import com.example.nfcapplication.data.NfcNdefMessage
import com.example.nfcapplication.utility.serialNumberFormatter
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NdefTagView(
    generalTagInfo: GeneralTagInformation,
    nfcNdefMessage: NfcNdefMessage,
    onBackButtonClicked: () -> Unit,
) {
    Column {
        NordicAppBar(
            text = stringResource(id = R.string.ndef_tag),
            onNavigationButtonClick = onBackButtonClicked
        )
        LazyColumn {
            item {
                GeneralTagView(generalTagInfo)
                NdefDataView(nfcNdefMessage)
                NdefRecordView(ndefRecords = nfcNdefMessage.ndefRecord)

            }
        }
    }
}

@Composable
fun GeneralTagView(generalTagInfo: GeneralTagInformation) {
    Text(
        text = stringResource(id = R.string.tag_info),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(8.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RowInABoxView(
                stringResource(id = R.string.ic_manufacturer),
                generalTagInfo.icManufacturerName
            )
            RowInABoxView(
                stringResource(id = R.string.serial_number),
                serialNumberFormatter(generalTagInfo.serialNumber)
            )
            RowInABoxView(
                stringResource(id = R.string.maximum_transceive_len),
                stringResource(
                    id = R.string.bytes,
                    generalTagInfo.maxTransceiveLength.toString()
                )
            )
            RowInABoxView(
                stringResource(id = R.string.transceive_time_out),
                stringResource(id = R.string.millisecond, generalTagInfo.transceiveTimeout)
            )

            Text(
                text = stringResource(id = R.string.available_tag_technologies),
                style = MaterialTheme.typography.titleMedium
            )
            generalTagInfo.tagTechnology.onEach {
                Text(text = it)
            }
        }
    }
}

@Composable
fun NdefDataView(nfcNdefMessage: NfcNdefMessage) {
    Text(
        text = stringResource(id = R.string.ndef_info),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(8.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RowInABoxView(
                stringResource(id = R.string.record_count),
                nfcNdefMessage.recordCount.toString()
            )
            RowInABoxView(
                stringResource(id = R.string.max_message_size),
                stringResource(
                    id = R.string.bytes,
                    nfcNdefMessage.maximumMessageSize.toString()
                )
            )
            RowInABoxView(
                stringResource(id = R.string.current_message_size),
                stringResource(
                    id = R.string.bytes,
                    nfcNdefMessage.currentMessageSize.toString()
                )
            )
            RowInABoxView(
                stringResource(id = R.string.ndef_type),
                ndefTypeFormatter(nfcNdefMessage.ndefType)
            )
            RowInABoxView(
                stringResource(id = R.string.nfc_data_access_type),
                nfcNdefMessage.getAccessDataType(isNdefWritable = nfcNdefMessage.isNdefWritable)
            )
        }
    }
}

fun ndefTypeFormatter(ndefType: String) =
    ndefType.split(".").last().replaceFirstChar(Char::titlecase)

@Preview
@Composable
fun NdefTagViewPreview() {
    NordicTheme {
        NdefTagView(
            generalTagInfo = GeneralTagInformation(
                serialNumber = "2345ca8709",
                tagTechnology = listOf("ISO", "NFCA", "NFCF"),
                tagType = "Type2",
            ),
            nfcNdefMessage = NfcNdefMessage(
                recordCount = 2,
                currentMessageSize = 108,
                maximumMessageSize = 117,
                isNdefWritable = false,
                ndefRecord = listOf(NdefRecord()),
                ndefType = ""
            ),
            onBackButtonClicked = {}
        )
    }
}

@Composable
fun RowInABoxView(key: String, value: String) {
    Row {
        Text(
            text = key,
            modifier = Modifier.padding(end = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = value)
    }
}

@Composable
fun NdefRecordView(ndefRecords: List<NdefRecord>) {
    Text(
        text = stringResource(id = R.string.record_info),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(8.dp)
    )
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        ndefRecords.forEachIndexed { index, ndefRecord ->
            var isExpanded by remember { mutableStateOf(false) }
            val icon = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ExpandLess
            Column {
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Record ${index+1}: Name of the Record",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleLarge
                    ) // todo find the name of the Record
                    Icon(
                        imageVector = icon, contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { isExpanded = !isExpanded }
                    )

                }
                Divider(thickness = 1.dp)
            }
            if (isExpanded) {
                Column(modifier = Modifier.padding(8.dp)) {
                    RowInABoxView("Type name format", ndefRecord.typeNameFormat)
                    RowInABoxView("Type", ndefRecord.type)
                    RowInABoxView("Payload length", ndefRecord.payloadLength.toString())
                    RowInABoxView("Payload Data", ndefRecord.payloadData)
                }
            }
        }
    }
}

@Preview
@Composable
fun NdefRecordViewPreview() {
    NordicTheme {
        NdefRecordView(ndefRecords = listOf(NdefRecord()))
    }
}
