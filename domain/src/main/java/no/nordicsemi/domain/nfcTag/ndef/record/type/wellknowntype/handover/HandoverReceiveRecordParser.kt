package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.HandoverReceive

internal object HandoverReceiveRecordParser {

    fun parse(record: NdefRecord): HandoverReceive {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverReceive(
            typeNameFormat = typeNameFormat,
            payloadType = "Handover Receive",
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}