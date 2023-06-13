package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RecordTitle
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayUriRecord(
    uriRecord: URIRecord,
    index: Int
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column(modifier = Modifier.padding(8.dp)) {
        RecordTitle(
            recordTitle = uriRecord.recordName,
            index = index,
            recordIcon = Icons.Default.Link,
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
                    description = uriRecord.typeNameFormat
                )
                uriRecord.payloadType?.let {
                    RowInCardView(
                        title = stringResource(id = R.string.record_type),
                        description = it
                    )
                }
                RowInCardView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        uriRecord.payloadLength.toString()
                    )
                )
                uriRecord.protocol?.let {
                    RowInCardView(
                        title = stringResource(id = R.string.protocol_field),
                        description = it
                    )
                }
                uriRecord.uri?.let {
                    RowInCardView(
                        title = stringResource(id = R.string.uri_field),
                        description = it
                    )
                }
                Row {
                    Text(
                        text = uriRecord.payloadFieldName,
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    ClickableTextView(stringResource(id = R.string.url_tag), uriRecord.actualUri)
                }
            }
        }
    }
}

@Preview
@Composable
fun DisplayUriRecordPreview() {
    NordicTheme {
        // Preview for Well-Known Uri record.
        Column {

            DisplayUriRecord(
                uriRecord = URIRecord(
                    typeNameFormat = "NFC Forum well-known type",
                    payloadLength = 22,
                    protocol = "https://www.",
                    uri = "nordicsemi.com",
                    actualUri = "https://www.nordicsemi.com"
                ),
                index = 2
            )
            Spacer(Modifier.height(16.dp))
            // Preview for absolute uri.
            DisplayUriRecord(
                uriRecord = URIRecord(
                    typeNameFormat = "Absolute Uri",
                    payloadLength = 22,
                    actualUri = "https://www.nordicsemi.com"
                ),
                index = 3
            )
        }
    }
}