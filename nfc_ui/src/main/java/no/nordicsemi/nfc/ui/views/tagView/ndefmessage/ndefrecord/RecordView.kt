package no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.nfc.ble.BleDevice
import no.nordicsemi.nfc.domain.nfcTag.ndef.NfcNdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.AndroidApplicationRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.GenericExternalType
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverCarrier
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverSelect
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.OtherRecords
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.Unknown
import no.nordicsemi.nfc.ui.R
import no.nordicsemi.nfc.ui.uicomponents.TitleView
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.external.DisplayAndroidPackageRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.external.DisplayGenericExternalTypeRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.handover.DisplayAlternativeCarrierRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.handover.DisplayHandoverCarrierRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.handover.DisplayHandoverReceiveRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.handover.DisplayHandoverSelectRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.handover.DisplayOtherRecords
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.wellknown.DisplayMimeTypeRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.wellknown.DisplaySmartPosterRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.wellknown.DisplayTextRecord
import no.nordicsemi.nfc.ui.views.tagView.ndefmessage.ndefrecord.wellknown.DisplayUriRecord

@Composable
fun RecordView(
    ndefRecords: List<NfcNdefRecord>,
    onBluetoothConnection: (BleDevice) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            TitleView(
                icon = painterResource(id = R.drawable.storage_icon),
                title = stringResource(id = R.string.record_info),
                modifier = Modifier.padding(8.dp),
                textStyle = MaterialTheme.typography.headlineSmall,
                isExpanded = null,
            )

            Divider(thickness = 1.dp)

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
                    is MimeRecord -> DisplayMimeTypeRecord(recordType, index, onBluetoothConnection)
                    is GenericExternalType -> DisplayGenericExternalTypeRecord(recordType, index)
                    is OtherRecords -> DisplayOtherRecords(record = recordType, index = index)
                    is Unknown -> TODO()
                    null -> TODO()
                }
            }
        }
    }
}

@Preview
@Composable
fun NdefRecordViewPreview() {
    NordicTheme {
        RecordView(ndefRecords = listOf(NfcNdefRecord()),
            onBluetoothConnection = {})
    }
}