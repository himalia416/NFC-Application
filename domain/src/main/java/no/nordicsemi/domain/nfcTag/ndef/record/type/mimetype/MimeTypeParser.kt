package no.nordicsemi.domain.nfcTag.ndef.record.type.mimetype

import android.nfc.NdefRecord
import no.nordic.handOverSelectMessageParser.HandOverDataParser
import no.nordic.handOverSelectMessageParser.data.BluetoothLeOobData
import no.nordisemi.utils.DataByteArray
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord

internal object MimeTypeParser {

    /**
     * Parse mime type ndef record.
     */
    fun parse(record: NdefRecord): MimeRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())
        val domainType = String(record.type, Charsets.UTF_8)
        val recordName = if (domainType == "application/vnd.bluetooth.le.oob") "Bluetooth Carrier Configuration LE Record" else "Mime Type Record"

        return MimeRecord(
            recordName = recordName,
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload),
            payloadData = DataByteArray(record.payload)
        )
    }
}