package com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.HandoverSelect

object HandoverSelectRecordParser {

    fun parse(record: NdefRecord): HandoverSelect {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverSelect(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}