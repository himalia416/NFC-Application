package com.example.domain.data

import android.os.Parcelable
import com.example.domain.constants.protocols
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

data class PayloadTypeAndRecord(
    val recordName: String,
    val payloadName: String
)

data class TextRecordStructure(
    val langCode: String,
    val actualText: String,
    val encoding: String
)

@Parcelize
data class NdefRecord(
    val typeNameFormat: String = "",
    val type: String = "",
    val payloadLength: Int = 0,
    val payloadData: ByteArray? = null,
) : Parcelable {

    // The RTD type name format is specified in NFCForum-TS-RTD_1.0.
    fun getRecordName(): PayloadTypeAndRecord {
        return when {
            typeNameFormat == TnfNameFormatter.NFC_RTD.tnf && type in setOf("T", "U", "Sp", "ac", "Hc", "Hr", "Hs") -> {
                val recordName = when (type) {
                    "T" -> "Text" // {in NFC binary encoding: 0x54}
                    "U" -> "URI"
                    "Sp" -> "Smart Poster"
                    "ac" -> "Alternative Carrier"
                    "Hc" -> "Handover Carrier"
                    "Hr" -> "Handover Request"
                    "Hs" -> "Handover Select"
                    else -> "Unknown Record Type"
                }
                PayloadTypeAndRecord(recordName, recordName)
            }
            typeNameFormat == TnfNameFormatter.EXTERNAL_NFC_RTD.tnf && type == "android.com:pkg" ->
                PayloadTypeAndRecord("Android Application", "Package")
            else ->
                PayloadTypeAndRecord("Unknown Record Type", "Payload")
        }
    }

    fun getUriProtocol(firstPayloadIndex: Int): String {
        // payload[0] contains the URI Identifier Code, as per
        // NFC Forum "URI Record Type Definition" section 3.2.2.
        return if (firstPayloadIndex in protocols.indices) {
            protocols[firstPayloadIndex]
        } else {
            "Invalid Protocol"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NdefRecord

        if (typeNameFormat != other.typeNameFormat) return false
        if (type != other.type) return false
        if (payloadLength != other.payloadLength) return false
        if (payloadData != null) {
            if (other.payloadData == null) return false
            if (!payloadData.contentEquals(other.payloadData)) return false
        } else if (other.payloadData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = typeNameFormat.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + payloadLength
        result = 31 * result + (payloadData?.contentHashCode() ?: 0)
        return result
    }


    fun getLanguageCode(payloadBytes: ByteArray): TextRecordStructure {
        val isUTF8 =
            payloadBytes[0].toInt() and 0x080 == 0 //status byte: bit 7 indicates encoding (0 = UTF-8, 1 = UTF-16)
        val encoding = if (isUTF8) Charsets.UTF_8 else Charsets.UTF_16
        val languageLength =
            payloadBytes[0].toInt() and 0x03F //status byte: bits 5..0 indicate length of language code
        val textLength = payloadBytes.size - 1 - languageLength
        val languageCode = String(payloadBytes, 1, languageLength, Charsets.US_ASCII)
        val payloadText =
            String(payloadBytes, 1 + languageLength, textLength, encoding)
        return TextRecordStructure(languageCode, payloadText, encoding.toString())
    }

}

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
    RFC_2046("Media-type"),
    RFC_3986("Absolute URI "),
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