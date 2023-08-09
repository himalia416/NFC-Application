package no.nordicsemi.ui.views.tagView.ndefmessage.ndefrecord.wellknown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import no.nordicsemi.bleconnection.BleDevice
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.handOverData.data.BluetoothLeOobData
import no.nordicsemi.nfcui.R
import no.nordicsemi.ui.uicomponents.NfcListView
import no.nordicsemi.ui.uicomponents.NfcRowView
import no.nordicsemi.ui.uicomponents.RecordTitleView
import no.nordisemi.utils.toPayloadData

@Composable
fun DisplayMimeTypeRecord(
    mimeRecord: MimeRecord,
    index: Int,
    onBluetoothConnection: (BleDevice) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        RecordTitleView(
            recordTitle = mimeRecord.recordName,
            index = index,
            modifier = Modifier.padding(8.dp),
            recordIcon = mimeRecord.getRecordIcon(),
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
                    description = mimeRecord.typeNameFormat
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_type),
                    description = mimeRecord.payloadType
                )
                NfcRowView(
                    title = stringResource(id = R.string.record_payload_len),
                    stringResource(
                        id = R.string.bytes,
                        mimeRecord.payloadLength.toString()
                    )
                )
                mimeRecord.payloadString?.let {
                    NfcRowView(
                        title = mimeRecord.payloadFieldName,
                        description = it
                    )
                }
                mimeRecord.payloadData?.let {
                    NfcRowView(
                        title = stringResource(id = R.string.payload_data),
                        description = it.value.toPayloadData()
                    )
                }
                mimeRecord.bluetoothLeOobData?.let {
                    BluetoothLeDataView(it)
                    Button(
                        onClick = {
                            onBluetoothConnection(BleDevice(it.bleDeviceAddress.address))
                        }) {
                        Text(text = stringResource(id = R.string.connect))
                    }
                }
            }
        }
    }
}

@Composable
private fun BluetoothLeDataView(bluetoothLeOobData: BluetoothLeOobData) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Divider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
        )
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp,
                start = 4.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NfcRowView(
                title = stringResource(id = R.string.ble_device_address),
                description = bluetoothLeOobData.bleAddressType
            )
            NfcRowView(
                title = stringResource(id = R.string.ble_device_address_type),
                description = bluetoothLeOobData.bleDeviceAddress.address
            )
            NfcRowView(
                title = stringResource(id = R.string.le_role_type),
                description = bluetoothLeOobData.roleType.toString()
            )

            bluetoothLeOobData.appearance?.let {
                NfcRowView(
                    title = stringResource(id = R.string.appearance),
                    description = it
                )
            }

            NfcListView(
                title = stringResource(id = R.string.flags),
                list = bluetoothLeOobData.flags
            )

            bluetoothLeOobData.localName?.let {
                NfcRowView(
                    title = stringResource(id = R.string.local_name),
                    description = it
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplayMimeTypeRecordPreview() {
    NordicTheme {
        DisplayMimeTypeRecord(
            mimeRecord = MimeRecord(
                typeNameFormat = "Media-type",
                payloadType = "application/vnd.bluetooth.le.oob",
                payloadLength = 89,
                payloadString = "Nordic_HRM_NFC"
            ),
            index = 2,
            onBluetoothConnection = {}
        )
    }
}