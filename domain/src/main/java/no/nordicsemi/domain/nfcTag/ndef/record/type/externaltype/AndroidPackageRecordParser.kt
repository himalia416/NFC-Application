package no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidPackage

object AndroidPackageRecordParser {

    fun parse(record: NdefRecord): AndroidPackage {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return AndroidPackage(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}