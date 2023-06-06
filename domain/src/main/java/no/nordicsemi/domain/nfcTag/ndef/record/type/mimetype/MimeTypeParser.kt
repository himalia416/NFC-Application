package no.nordicsemi.domain.nfcTag.ndef.record.type.mimetype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.MimeRecord

internal object MimeTypeParser {

    /**
     * Parse mime type ndef record.
     */
    fun parse(record: NdefRecord): MimeRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return MimeRecord(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}