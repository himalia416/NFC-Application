package com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.HandoverReceive

object HandoverReceiveRecordParser {

    fun parse(record: NdefRecord): HandoverReceive {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        return HandoverReceive(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            payload = String(record.payload)
        )
    }
}