package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidPackage
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView

@Composable
fun DisplayAndroidPackageRecord(
    androidPackageRecord: AndroidPackage,
    index: Int
) {
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
            firstItem = stringResource(id = R.string.record_type_name_format),
            secondItem = androidPackageRecord.typeNameFormat
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_type),
            secondItem = androidPackageRecord.payloadType
        )
        RowInCardView(
            firstItem = stringResource(id = R.string.record_payload_len),
            stringResource(
                id = R.string.bytes,
                androidPackageRecord.payloadLength.toString()
            )
        )
        RowInCardView(
            firstItem = androidPackageRecord.payloadFieldName,
            secondItem = androidPackageRecord.payload
        )
    }
}

@Preview
@Composable
fun DisplayAndroidPackageRecordPreview() {
    NordicTheme {
        DisplayAndroidPackageRecord(
            androidPackageRecord = AndroidPackage(
                payloadLength = 22,
                payload = "NordicSemiconductor ASA"
            ),
            index = 2
        )
    }
}