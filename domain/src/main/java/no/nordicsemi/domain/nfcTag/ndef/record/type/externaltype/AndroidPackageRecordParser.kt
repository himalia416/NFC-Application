package no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype

import android.nfc.NdefRecord
import no.nordic.handOverSelectMessageParser.utility.DataByteArray
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.AndroidApplicationRecord

object AndroidPackageRecordParser {

    /**
     * Domain and type indicating an Android Application Record.
     */
    const val DOMAIN = "android.com"
    const val TYPE = "pkg"

    fun parse(record: NdefRecord): AndroidApplicationRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return AndroidApplicationRecord(
            typeNameFormat = typeNameFormat,
            payloadType = "Android package",
            payloadLength = record.payload.size,
            payload = String(record.payload),
            payloadData = DataByteArray(record.payload)
        )
    }
}