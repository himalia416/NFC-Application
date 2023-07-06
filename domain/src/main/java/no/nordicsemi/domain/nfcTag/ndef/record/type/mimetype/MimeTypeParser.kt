package no.nordicsemi.domain.nfcTag.ndef.record.type.mimetype

import android.nfc.NdefRecord
import no.nordicsemi.handOverData.HandOverDataParser
import no.nordicsemi.handOverData.data.BluetoothLeOobData
import no.nordisemi.utils.DataByteArray
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord
import java.nio.ByteBuffer
import javax.inject.Inject

class MimeTypeParser @Inject constructor(
    private val handOverData: HandOverDataParser,
) {
    var bluetoothLeOobData: BluetoothLeOobData? = null

    /**
     * Parse mime type ndef record.
     */
    fun parse(record: NdefRecord): MimeRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())
        var recordName = "Mime Type Record"
        val domainType = String(record.type, Charsets.UTF_8)

        if (domainType == "application/vnd.bluetooth.le.oob") {
            bluetoothLeOobData = handOverData.parser(ByteBuffer.wrap(record.payload))
            recordName = "Bluetooth Carrier Configuration LE Record"
        }

        return MimeRecord(
            recordName = recordName,
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload),
            payloadData = DataByteArray(record.payload),
            bluetoothLeOobData = bluetoothLeOobData
        )
    }
}