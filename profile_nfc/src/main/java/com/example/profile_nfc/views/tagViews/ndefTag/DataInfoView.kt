package com.example.profile_nfc.views.tagViews.ndefTag

import androidx.compose.foundation.clickable
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
import com.example.domain.nfcTag.ndef.NdefRecord
import com.example.domain.nfcTag.ndef.NfcNdefMessage
import com.example.profile_nfc.R
import com.example.profile_nfc.component.RowInCardView
import com.example.profile_nfc.component.TitleWithIcon
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun DataInfoView(
    nfcNdefMessage: NfcNdefMessage,
    isShowRecordsExpanded: Boolean,
    onShowRecordClicked: () -> Unit
) {
    val recordExpandText = if (!isShowRecordsExpanded) stringResource(id = R.string.expand_records)
    else stringResource(id = R.string.hide_records)
    Column {
        TitleWithIcon(
            icon = painterResource(id = R.drawable.alpha_n_box),
            title = stringResource(id = R.string.ndef_info),
            modifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.headlineSmall
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                RowInCardView(
                    firstItem = stringResource(id = R.string.max_message_size),
                    secondItem = stringResource(
                        id = R.string.bytes,
                        nfcNdefMessage.maximumMessageSize.toString()
                    )
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.current_message_size),
                    secondItem = stringResource(
                        id = R.string.bytes,
                        nfcNdefMessage.currentMessageSize.toString()
                    )
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.ndef_type),
                    secondItem = nfcNdefMessage.ndefType
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.nfc_data_access_type),
                    secondItem = nfcNdefMessage.getAccessDataType(
                        isNdefWritable = nfcNdefMessage.isNdefWritable
                    )
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.record_count),
                    secondItem = nfcNdefMessage.recordCount.toString()
                )
                Text(
                    text = recordExpandText,
                    modifier = Modifier.clickable(onClick = onShowRecordClicked),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
fun DataInfoViewPreview() {
    NordicTheme {
        DataInfoView(
            nfcNdefMessage = NfcNdefMessage(
                recordCount = 2,
                currentMessageSize = 108,
                maximumMessageSize = 117,
                isNdefWritable = false,
                ndefRecord = listOf(NdefRecord()),
                ndefType = "Type 2"
            ),
            isShowRecordsExpanded = false,
            onShowRecordClicked = {}
        )
    }
}
