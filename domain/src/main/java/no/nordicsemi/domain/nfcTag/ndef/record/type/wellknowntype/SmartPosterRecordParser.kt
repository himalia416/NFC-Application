package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.SmartPoster

internal object SmartPosterRecordParser {

    fun parse(record: NdefRecord): SmartPoster {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return SmartPoster(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}