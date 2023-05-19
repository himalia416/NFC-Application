package com.example.domain.mapper

import com.example.domain.data.AlternativeCarrier
import com.example.domain.data.AndroidPackage
import com.example.domain.data.HandoverCarrier
import com.example.domain.data.HandoverReceive
import com.example.domain.data.HandoverSelect
import com.example.domain.data.NdefRecordType
import com.example.domain.data.OtherExternalType
import com.example.domain.data.SmartPoster
import com.example.domain.data.TextRecordStructure
import com.example.domain.data.TnfNameFormatter
import com.example.domain.data.URIRecordStructure

// The RTD type name format is specified in NFCForum-TS-RTD_1.0.
object NdefRecordTypeMapper {
    fun getNdefRecordType(typeNameFormat: String, type: String): NdefRecordType {
        return when (typeNameFormat) {
            TnfNameFormatter.TNF_WELL_KNOWN.tnf -> when (type) {
                String(byteArrayOf(0x54)) -> TextRecordStructure()
                String(byteArrayOf(0x55)) -> URIRecordStructure()
                String(byteArrayOf(0x53, 0x70)) -> SmartPoster()
                String(byteArrayOf(0x61, 0x63)) -> AlternativeCarrier()
                String(byteArrayOf(0x48, 0x63)) -> HandoverCarrier()
                String(byteArrayOf(0x48, 0x72)) -> HandoverReceive()
                String(byteArrayOf(0x48, 0x73)) -> HandoverSelect()
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> when (type) {
                "android.com:pkg" -> AndroidPackage()
                else -> OtherExternalType()
            }

            else -> throw IllegalArgumentException("Unknown Record Type")
        }
    }
}
