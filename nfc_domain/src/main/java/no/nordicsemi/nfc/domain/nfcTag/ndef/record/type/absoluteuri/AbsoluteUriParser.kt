package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.absoluteuri

import android.nfc.NdefRecord
import no.nordicsemi.nfc.utils.DataByteArray
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.RecordNameParser
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.URIRecord

internal object AbsoluteUriParser {
    /** Parse an absolute URI record  */
    fun parse(record: NdefRecord): URIRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())
        val actualUri = String(record.payload)

        return URIRecord(
            recordName = RecordNameParser.parse(String(record.type)),
            typeNameFormat = typeNameFormat,
            payloadLength = record.payload.size,
            actualUri = actualUri,
            payloadType = "Absolute Uri",
            payloadData = DataByteArray(record.payload)
        )
    }
}