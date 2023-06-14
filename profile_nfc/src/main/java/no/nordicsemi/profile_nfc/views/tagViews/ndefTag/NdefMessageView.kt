package no.nordicsemi.profile_nfc.views.tagViews.ndefTag

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.NfcRowView
import no.nordicsemi.profile_nfc.component.TitleView

@Composable
fun NdefMessageView(
    nfcNdefMessage: NfcNdefMessage,
) {
    var expanded by rememberSaveable { mutableStateOf(true) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            TitleView(
                icon = painterResource(id = R.drawable.alpha_n_box),
                title = stringResource(id = R.string.ndef_info),
                modifier = Modifier.padding(8.dp),
                textStyle = MaterialTheme.typography.headlineSmall,
                isExpanded = expanded,
                onExpandClicked = { expanded = !expanded }
            )
            AnimatedVisibility(
                visible = expanded,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NfcRowView(
                        title = stringResource(id = R.string.max_message_size),
                        description = stringResource(
                            id = R.string.bytes,
                            nfcNdefMessage.maximumMessageSize.toString()
                        )
                    )
                    NfcRowView(
                        title = stringResource(id = R.string.current_message_size),
                        description = stringResource(
                            id = R.string.bytes,
                            nfcNdefMessage.currentMessageSize.toString()
                        )
                    )
                    NfcRowView(
                        title = stringResource(id = R.string.ndef_type),
                        description = nfcNdefMessage.ndefType
                    )
                    NfcRowView(
                        title = stringResource(id = R.string.nfc_data_access_type),
                        description = nfcNdefMessage.getAccessDataType(
                            isNdefWritable = nfcNdefMessage.isNdefWritable
                        )
                    )
                    NfcRowView(
                        title = stringResource(id = R.string.record_count),
                        description = nfcNdefMessage.recordCount.toString()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NdefMessageViewPreview() {
    NordicTheme {
        NdefMessageView(
            nfcNdefMessage = NfcNdefMessage(
                recordCount = 2,
                currentMessageSize = 108,
                maximumMessageSize = 117,
                isNdefWritable = false,
                ndefRecord = listOf(NdefRecord()),
                ndefType = "Type 2"
            ),
        )
    }
}