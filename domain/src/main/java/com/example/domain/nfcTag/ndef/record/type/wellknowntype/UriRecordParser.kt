package com.example.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.URIRecord
import com.example.domain.nfcTag.ndef.record.mapper.UriProtocolMapper

object UriRecordParser {

    fun parse(record: NdefRecord): URIRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        val status = record.payload[0].toInt()
        val protocolField = UriProtocolMapper.getUriPrefix(status)
        return URIRecord(
            typeNameFormat = typeNameFormat,
            payloadType = String(record.type),
            payloadLength = record.payload.size,
            protocol = protocolField,
            actualUri = String(record.payload),
        )
    }
}