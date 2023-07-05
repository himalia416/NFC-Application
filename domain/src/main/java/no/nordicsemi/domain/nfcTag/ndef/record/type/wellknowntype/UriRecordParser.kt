package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import no.nordic.handOverSelectMessageParser.utility.DataByteArray
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.domain.nfcTag.ndef.record.mapper.UriProtocolMapper

/**
 * payload[0] contains the URI Identifier Code, per the
 * NFC Forum "URI Record Type Definition" section 3.2.2.
 *
 * payload[1]...payload[payload.length - 1] contains the rest of
 * the URI.
 */
internal object UriRecordParser {

    fun parse(record: NdefRecord): URIRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        val status = record.payload[0].toInt()
        val protocolField = UriProtocolMapper.getUriPrefix(status)
        val actualUri = String(record.payload, 1, record.payload.size - 1)
        return URIRecord(
            typeNameFormat = typeNameFormat,
            payloadType = "URI",
            payloadLength = record.payload.size,
            protocol = protocolField,
            uri = actualUri,
            actualUri = protocolField + actualUri,
            payloadData = DataByteArray(record.payload)
        )
    }
}