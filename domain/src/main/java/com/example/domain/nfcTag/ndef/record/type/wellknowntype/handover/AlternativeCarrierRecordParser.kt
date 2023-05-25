package com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.AlternativeCarrier

internal object AlternativeCarrierRecordParser {

    fun parse(record: NdefRecord): AlternativeCarrier {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return AlternativeCarrier(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}