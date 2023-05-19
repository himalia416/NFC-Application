package com.example.domain.mapper

import com.example.domain.data.AlternativeCarrier
import com.example.domain.data.AndroidPackage
import com.example.domain.data.HandoverCarrier
import com.example.domain.data.HandoverReceive
import com.example.domain.data.HandoverSelect
import com.example.domain.data.NdefRecordType
import com.example.domain.data.SmartPoster
import com.example.domain.data.TextRecordStructure
import com.example.domain.data.TnfNameFormatter
import com.example.domain.data.URIRecordStructure

// The RTD type name format is specified in NFCForum-TS-RTD_1.0.
object NdefRecordTypeMapper {
    fun getNdefRecordType(typeNameFormat: String, type: String): NdefRecordType {
        return when (typeNameFormat) {
            TnfNameFormatter.TNF_WELL_KNOWN.tnf -> when (type) {
                "T" -> TextRecordStructure()
                "U" -> URIRecordStructure()
                "Sp" -> SmartPoster()
                "ac" -> AlternativeCarrier()
                "Hc" -> HandoverCarrier()
                "Hr" -> HandoverReceive()
                "Hs" -> HandoverSelect()
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> when (type) {
                "android.com:pkg" -> AndroidPackage()
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            else -> throw IllegalArgumentException("Unknown Record Type")
        }
    }
}