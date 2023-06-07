package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.core.AppLauncher
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayGenericExternalTypeRecord(
    externalTypeRecord: GenericExternalType,
    index: Int,
) {
    val context: Context = LocalContext.current
    val packageManager: PackageManager = context.packageManager

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(
                id = R.string.record_name,
                index + 1,
                externalTypeRecord.recordName
            ),
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            RowInCardView(
                firstItem = stringResource(id = R.string.record_type_name_format),
                secondItem = externalTypeRecord.typeNameFormat
            )
            RowInCardView(
                firstItem = stringResource(id = R.string.record_type),
                secondItem = externalTypeRecord.payloadType
            )
            RowInCardView(
                firstItem = stringResource(id = R.string.record_payload_len),
                stringResource(
                    id = R.string.bytes,
                    externalTypeRecord.payloadLength.toString()
                )
            )
            Row {
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