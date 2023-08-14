package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.HandoverSelect
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.RecordNameParser

internal object HandoverSelectRecordParser {

    fun parse(record: NdefRecord): HandoverSelect {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverSelect(
            recordName = RecordNameParser.parse(String(record.type)),
            typeNameFormat = typeNameFormat,
            payloadType = "Handover Select",
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}