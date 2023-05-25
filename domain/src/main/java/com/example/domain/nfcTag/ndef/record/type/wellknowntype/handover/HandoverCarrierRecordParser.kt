package com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.HandoverCarrier

internal object HandoverCarrierRecordParser {

    fun parse(record: NdefRecord): HandoverCarrier {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverCarrier(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}