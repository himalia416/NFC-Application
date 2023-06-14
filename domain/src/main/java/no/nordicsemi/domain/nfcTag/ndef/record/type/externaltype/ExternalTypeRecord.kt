package no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.ExternalType
import no.nordicsemi.domain.nfcTag.ndef.record.GenericExternalType

internal object ExternalTypeRecord {

    /**
     * Parses External type record.
     */
    fun parse(record: NdefRecord): ExternalType {
        val domainType = String(record.type, Charsets.UTF_8)

        val colon = domainType.lastIndexOf(':')

        val type: String?
        val domain: String
        if (colon == -1) {
            domain = domainType
            type = null
        } else {
            domain = domainType.substring(0, colon)
            type = if (colon + 1 < domainType.length) {
                domainType.substring(colon + 1)
            } else ""
        }

        return if (domain == AndroidPackageRecordParser.DOMAIN && type == AndroidPackageRecordParser.TYPE) {
            AndroidPackageRecordParser.parse(record)
        } else GenericExternalType(
            typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt()),
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            domain = domain,
            domainType = type,
            payload = String(record.payload),
            payloadData = record.payload
        )
    }
}