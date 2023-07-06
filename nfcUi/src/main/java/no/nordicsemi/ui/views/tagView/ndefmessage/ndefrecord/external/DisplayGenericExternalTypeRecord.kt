package no.nordicsemi.ui.views.tagView.ndefmessage.ndefrecord.external

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explicit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.ui.uicomponents.NfcRowView
import no.nordicsemi.ui.uicomponents.RecordTitleView
import no.nordicsemi.android.common.core.AppLauncher
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.nfcui.R
import no.nordisemi.utils.toPayloadData

@Composable
fun DisplayGenericExternalTypeRecord(
    externalTypeRecord: GenericExternalType,
    index: Int,
) {
    val context: Context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = externalTypeRecord.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = Icons.Default.Explicit,
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
                    description = externalTypeRecord.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = externalTypeRecord.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        externalTypeRecord.payloadLength.toString()
                    )
                )
                Column {
                    Text(
                        text = externalTypeRecord.domainType ?: stringResource(id = R.string.data),
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = externalTypeRecord.payload,
                        modifier = Modifier.clickable {
                            AppLauncher.lunch(
                                packageManager = packageManager,
                                packageName = externalTypeRecord.payload,
                                context = context
                            )
                        },
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                externalTypeRecord.payloadData?.let {
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
fun DisplayGenericExternalTypeRecordPreview() {
    NordicTheme {
        DisplayGenericExternalTypeRecord(
            externalTypeRecord = GenericExternalType(
                recordName = "Bluetooth Carrier Configuration LE Record",
                typeNameFormat = "Media-type",
                payloadType = "example.com:foobar",
                payloadLength = 31,
                domain = "example.com",
                domainType = "foobar",
                payload = "com.example.foobar"
            ),
            index = 1
        )
    }
}