package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverReceive
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.RecordNameParser

internal object HandoverReceiveRecordParser {

    fun parse(record: NdefRecord): HandoverReceive {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverReceive(
            recordName = RecordNameParser.parse(String(record.type)),
            typeNameFormat = typeNameFormat,
            payloadType = "Handover Receive",
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}