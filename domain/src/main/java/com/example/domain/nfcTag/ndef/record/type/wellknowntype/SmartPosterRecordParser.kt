package com.example.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.SmartPoster

object SmartPosterRecordParser {

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