package com.example.domain.nfcTag.ndef.record.mapper

import android.nfc.NdefRecord
import com.example.domain.nfcTag.ndef.TnfNameFormatter
import com.example.domain.nfcTag.ndef.record.NdefRecordType
import com.example.domain.nfcTag.ndef.record.OtherExternalType
import com.example.domain.nfcTag.ndef.record.type.externaltype.AndroidPackageRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.SmartPosterRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.TextRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.UriRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover.AlternativeCarrierRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverCarrierRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverReceiveRecordParser
import com.example.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverSelectRecordParser

// The RTD type name format is specified in NFCForum-TS-RTD_1.0.
object NdefRecordTypeMapper {
    private val RTD_TEXT = byteArrayOf(0x54) // "T"
    private val RTD_URI = byteArrayOf(0x55) // "U"
    private val RTD_SMART_POSTER = byteArrayOf(0x53, 0x70) // "Sp"
    private val RTD_ALTERNATIVE_CARRIER = byteArrayOf(0x61, 0x63) // "ac"
    private val RTD_HANDOVER_CARRIER = byteArrayOf(0x48, 0x63)  // "Hc"
    private val RTD_HANDOVER_REQUEST = byteArrayOf(0x48, 0x72)  // "Hr"
    private val RTD_HANDOVER_SELECT = byteArrayOf(0x48, 0x73) // "Hs"
    private val RTD_ANDROID_APP = "android.com:pkg".toByteArray()

    fun getNdefRecordType(record: NdefRecord): NdefRecordType {
        val type = record.type

        return when (TnfNameFormatter.getTnfName(record.tnf.toInt())) {
            TnfNameFormatter.TNF_WELL_KNOWN.tnf-> when  {
                type.contentEquals(RTD_TEXT) -> TextRecordParser.parse(record)
                type.contentEquals(RTD_URI) -> UriRecordParser.parse(record)
                type.contentEquals(RTD_SMART_POSTER) -> SmartPosterRecordParser.parse(record)
                type.contentEquals(RTD_ALTERNATIVE_CARRIER) -> AlternativeCarrierRecordParser.parse(record)
                type.contentEquals(RTD_HANDOVER_CARRIER) -> HandoverCarrierRecordParser.parse(record)
                type.contentEquals(RTD_HANDOVER_REQUEST) -> HandoverReceiveRecordParser.parse(record)
                type.contentEquals(RTD_HANDOVER_SELECT) -> HandoverSelectRecordParser.parse(record)
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> when  {
                type.contentEquals(RTD_ANDROID_APP) -> AndroidPackageRecordParser.parse(record)
                else -> OtherExternalType(payloadLength = record.payload.size)
            }

            else -> throw IllegalArgumentException("Unknown Record Type")
        }
    }
}
