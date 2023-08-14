package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.OtherRecords
import no.nordicsemi.domain.nfcTag.ndef.record.RecordNameParser
import no.nordisemi.utils.DataByteArray

object OtherRecordParser {

    /**
     * Parses all other type of records which are not handled yet.
     */
    fun parse(record: NdefRecord): OtherRecords {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())
        return OtherRecords(
            recordName = RecordNameParser.parse(String(record.type)),
            typeNameFormat = typeNameFormat,
            payloadType = RecordNameParser.parse(String(record.type)),
            payloadLength = record.payload.size,
            payload = String(record.payload),
            payloadData = DataByteArray(record.payload)
        )
    }
}