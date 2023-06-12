package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
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
import no.nordicsemi.android.common.core.AppLauncher
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RecordTitle
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayAndroidPackageRecord(
    androidPackageRecord: AndroidApplicationRecord,
    index: Int
) {
    val context: Context = LocalContext.current
    val packageManager: PackageManager = context.packageManager
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        RecordTitle(
            recordTitle = androidPackageRecord.recordName,
            index = index,
            recordIcon = Icons.Default.Android,
            isExpanded = isExpanded,
            onExpandClicked = { isExpanded = !isExpanded }
        )
        if (isExpanded) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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