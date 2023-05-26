package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.domain.nfcTag.ndef.record.mapper.UriProtocolMapper

internal object UriRecordParser {

    fun parse(record: NdefRecord): URIRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        val status = record.payload[0].toInt()
        val protocolField = UriProtocolMapper.getUriPrefix(status)
        val actualUri = String(record.payload, 1, record.payload.size-1)
        return URIRecord(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            protocol = protocolField,
            actualUri = actualUri,
        )
    }
}