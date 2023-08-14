package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.mimetype

import android.nfc.NdefRecord
import no.nordicsemi.nfc.handover.HandOverDataParser
import no.nordicsemi.nfc.handover.data.BluetoothLeOobData
import no.nordicsemi.nfc.utils.DataByteArray
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.MimeRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.RecordNameParser
import java.nio.ByteBuffer
import javax.inject.Inject

class MimeTypeParser @Inject constructor(
    private val handOverData: HandOverDataParser,
) {
    private var bluetoothLeOobData: BluetoothLeOobData? = null

    /**
     * Parse mime type ndef record.
     */
    fun parse(record: NdefRecord): MimeRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())
        var recordName = RecordNameParser.parse(String(record.type))
        val domainType = String(record.type, Charsets.UTF_8)
        var payloadString: String? = String(record.payload)

        if (domainType == BLE_OOB_DATA_TYPE) {
            bluetoothLeOobData = handOverData.parser(ByteBuffer.wrap(record.payload))
            recordName = "Bluetooth Carrier Configuration LE Record"
            payloadString = null
        }

        return MimeRecord(
            recordName = recordName,
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payloadString = payloadString,
            payloadData = DataByteArray(record.payload),
            bluetoothLeOobData = bluetoothLeOobData
        )
    }

    companion object {
        private const val BLE_OOB_DATA_TYPE = "application/vnd.bluetooth.le.oob"
    }
}