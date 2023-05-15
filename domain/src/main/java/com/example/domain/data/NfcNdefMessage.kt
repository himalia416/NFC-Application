package com.example.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcNdefMessage(
    val recordCount: Int,
    val currentMessageSize: Int,
    val maximumMessageSize: Int,
    val isNdefWritable: Boolean,
    val ndefRecord: List<NdefRecord>,
    val ndefType: String
) : Parcelable {
    fun getAccessDataType(isNdefWritable: Boolean) =
        if (isNdefWritable) "Read/write" else "Read-only"
}

@Parcelize
data class NdefRecord(
    val typeNameFormat: String = "",
    val type: String = "",
    val payloadLength: Int = 0,
    val payloadData: String = "",
) : Parcelable

enum class NdefTagType(val type: String) {
    NFC_FORUM_TYPE_1("NFC Forum Type 1"),
    NFC_FORUM_TYPE_2("NFC Forum Type 2"),
    NFC_FORUM_TYPE_3("NFC Forum Type 3"),
    NFC_FORUM_TYPE_4("NFC Forum Type 4"),
    MIFARE_CLASSIC("MiFare Classic");

    companion object {
        fun getTagType(typeS: String): String {
            return when (typeS.split(".").last()) {
                "type1" -> NFC_FORUM_TYPE_1.type
                "type2" -> NFC_FORUM_TYPE_2.type
                "type3" -> NFC_FORUM_TYPE_3.type
                "type4" -> NFC_FORUM_TYPE_4.type
                "mifareclassic" -> MIFARE_CLASSIC.type
                else -> "Unsupported Type"
            }
        }
    }
}

enum class TnfNameFormatter(val tnf: String) {
    Empty("Empty"),
    NFC_RTD("NFC Forum well-known type"),
    RFC_2046("Media-type [RFC 2046]"),
    RFC_3986("Absolute URI [RFC 3986]"),
    EXTERNAL_NFC_RTD("NFC Forum external type"),
    Unknown("Unknown"),
    Unchanged("Unchanged"),
    Reserved("Reserved");

    companion object {
        fun getTnfName(tnfValue: Int): String {
            return values().find { it.ordinal == tnfValue }?.tnf ?: "Invalid Value"
        }
    }
}