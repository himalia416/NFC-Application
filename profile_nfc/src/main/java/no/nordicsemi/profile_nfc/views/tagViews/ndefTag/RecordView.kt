package no.nordicsemi.profile_nfc.views.tagViews.ndefTag

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
import no.nordicsemi.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverCarrier
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverSelect
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.domain.nfcTag.ndef.record.Unknown
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.TitleWithIcon
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayAlternativeCarrierRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayAndroidPackageRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayGenericExternalTypeRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayHandoverCarrierRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayHandoverReceiveRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayHandoverSelectRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayMimeTypeRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplaySmartPosterRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayTextRecord
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record.DisplayUriRecord

@Composable
fun RecordView(ndefRecords: List<NdefRecord>) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            TitleWithIcon(
                icon = painterResource(id = R.drawable.storage_icon),
                title = stringResource(id = R.string.record_info),
                modifier = Modifier.padding(8.dp),
                textStyle = MaterialTheme.typography.headlineSmall,
                isExpanded = expanded,
                onExpandClicked = { expanded = !expanded }
            )

            if (expanded){
                ndefRecords.forEachIndexed { index, ndefRecord ->
                    when (val recordType = ndefRecord.record) {
                        is TextRecord -> DisplayTextRecord(recordType, index)
                        is URIRecord -> DisplayUriRecord(recordType, index)
                        is AndroidApplicationRecord -> DisplayAndroidPackageRecord(recordType, index)
                        is SmartPoster -> DisplaySmartPosterRecord(recordType, index)
                        is AlternativeCarrier -> DisplayAlternativeCarrierRecord(recordType, index)
                        is HandoverCarrier -> DisplayHandoverCarrierRecord(recordType, index)
                        is HandoverReceive -> DisplayHandoverReceiveRecord(recordType, index)
                        is HandoverSelect -> DisplayHandoverSelectRecord(recordType, index)
                        is MimeRecord -> DisplayMimeTypeRecord(recordType, index)
                        is GenericExternalType -> DisplayGenericExternalTypeRecord(recordType, index)
                        is Unknown -> TODO()
                        else -> TODO()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NdefRecordViewPreview() {
    NordicTheme {
        RecordView(ndefRecords = listOf(NdefRecord()))
    }
}