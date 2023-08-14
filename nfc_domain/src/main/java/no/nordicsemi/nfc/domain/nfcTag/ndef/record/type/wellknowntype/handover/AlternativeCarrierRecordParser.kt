package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.AlternativeCarrier
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.RecordNameParser

internal object AlternativeCarrierRecordParser {

    fun parse(record: NdefRecord): AlternativeCarrier {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return AlternativeCarrier(
            recordName = RecordNameParser.parse(String(record.type)),
            typeNameFormat = typeNameFormat,
            payloadType = "Alternative Carrier",
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}