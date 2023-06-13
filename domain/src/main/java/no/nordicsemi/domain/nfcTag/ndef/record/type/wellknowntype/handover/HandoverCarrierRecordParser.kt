package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverCarrier

internal object HandoverCarrierRecordParser {

    fun parse(record: NdefRecord): HandoverCarrier {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverCarrier(
            typeNameFormat = typeNameFormat,
            payloadType = "Handover Carrier",
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}