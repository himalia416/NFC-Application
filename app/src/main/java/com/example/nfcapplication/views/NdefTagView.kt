package com.example.nfcapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
            text = stringResource(id = R.string.app_name),
            onNavigationButtonClick = onBackButtonClicked
        )
        Column {
            Text(
                text = stringResource(id = R.string.tag_info),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    KeyValueRow(
                        stringResource(id = R.string.ic_manufacturer),
                        generalTagInfo.icManufacturerName
                    )
                    KeyValueRow(
                        stringResource(id = R.string.serial_number),
                        serialNumberFormatter(generalTagInfo.serialNumber)
                    )
                    KeyValueRow(
                        stringResource(id = R.string.maximum_transceive_len),
                        stringResource(
                            id = R.string.bytes,
                            generalTagInfo.maxTransceiveLength.toString()
                        )
                    )
                    KeyValueRow(
                        stringResource(id = R.string.transceive_time_out),
                        stringResource(id = R.string.ms, generalTagInfo.transceiveTimeout)
                    )

                    Text(
                        text = stringResource(id = R.string.available_tag_technologies),
                        style = MaterialTheme.typography.titleMedium
                    )
                    LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(items = generalTagInfo.tagTechnology) { eachTag ->
                            Text(text = eachTag)
                        }
                    }
                }
            }
            Text(
                text = stringResource(id = R.string.ndef_info),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    KeyValueRow(
                        stringResource(id = R.string.record_count),
                        nfcNdefMessage.recordCount.toString()
                    )
                    KeyValueRow(
                        stringResource(id = R.string.max_message_size),
                        stringResource(
                            id = R.string.bytes,
                            nfcNdefMessage.maximumMessageSize.toString()
                        )
                    )
                    KeyValueRow(
                        stringResource(id = R.string.current_message_size),
                        stringResource(
                            id = R.string.bytes,
                            nfcNdefMessage.currentMessageSize.toString()
                        )
                    )
                    KeyValueRow(
                        stringResource(id = R.string.ndef_type),
                        nfcNdefMessage.ndefType.split(".").last()
                    )
                    KeyValueRow(
                        stringResource(id = R.string.nfc_data_access_type),
                        nfcNdefMessage.getAccessDataType(isNdefWritable = nfcNdefMessage.isNdefWritable)
                    )
                }
            }
        }
    }
}

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
            nfcNdefMessage = NfcNdefMessage(2, 108, 117, false, NdefRecord(), ""),
            onBackButtonClicked = {}
        )
    }
}

@Composable
fun KeyValueRow(key: String, value: String) {
    Row {
        Text(
            text = key,
            modifier = Modifier.padding(end = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = value)
    }
}