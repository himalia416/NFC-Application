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
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayAndroidPackageRecord(
    androidPackageRecord: AndroidApplicationRecord,
    index: Int
) {
    val context: Context = LocalContext.current
    val packageManager: PackageManager = context.packageManager

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(
                id = R.string.record_name,
                index + 1,
                androidPackageRecord.recordName
            ),
            modifier = Modifier.padding(8.dp)
        )
        Column(modifier = Modifier.padding(8.dp)) {
            RowInCardView(
                title = stringResource(id = R.string.record_type_name_format),
                description = androidPackageRecord.typeNameFormat
            )
            RowInCardView(
                title = stringResource(id = R.string.record_type),
                description = androidPackageRecord.payloadType
            )
            RowInCardView(
                title = stringResource(id = R.string.record_payload_len),
                stringResource(
                    id = R.string.bytes,
                    androidPackageRecord.payloadLength.toString()
                )
            )
            Row {
                Text(
                    text = androidPackageRecord.packageType,
                    modifier = Modifier.padding(end = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = androidPackageRecord.payload,
                    modifier = Modifier.clickable {
                        AppLauncher.lunch(
                            packageManager = packageManager,
                            packageName = androidPackageRecord.payload,
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
fun DisplayAndroidPackageRecordPreview() {
    NordicTheme {
        DisplayAndroidPackageRecord(
            androidPackageRecord = AndroidApplicationRecord(
                payloadLength = 22,
                payload = "no.nordicsemi.android.nrfthingy"
            ),
            index = 2
        )
    }
}