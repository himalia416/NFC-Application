package com.example.domain.data

import android.os.Parcelable
import com.example.domain.mapper.UriProtocolMapper
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
    val payloadData: ByteArray? = null,
) : Parcelable {

    fun getTextData(payloadBytes: ByteArray): TextRecordStructure {
        //status byte: bit 7 indicates encoding (0 = UTF-8, 1 = UTF-16)
        val isUTF8 = payloadBytes[0].toInt() and 0x080 == 0
        val encoding = if (isUTF8) Charsets.UTF_8 else Charsets.UTF_16
        //status byte: bits 5..0 indicate length of language code
        val languageLength = payloadBytes[0].toInt() and 0x03F
        val textLength = payloadBytes.size - 1 - languageLength
        val languageCode = String(payloadBytes, 1, languageLength, Charsets.US_ASCII)
        val payloadText = String(payloadBytes, 1 + languageLength, textLength, encoding)
        return TextRecordStructure(
            payloadType = type,
            langCode = languageCode,
            encoding = encoding.toString(),
            actualText = payloadText
        )
    }

    fun getUriData(payloadData: ByteArray): URIRecordStructure {
        val protocolField = UriProtocolMapper.getUriPrefix(payloadData[0].toInt())
        val actualPayload = String(payloadData)
        return URIRecordStructure(
            payloadType = type,
            protocol = protocolField,
            actualUri = actualPayload,
        )
    }

    fun getAndroidPackageData(payloadData: ByteArray): AndroidPackage = AndroidPackage(
        payloadType = type,
        payload = String(payloadData)
    )

    fun getSmartPosterData(payloadData: ByteArray): SmartPoster = SmartPoster(
        payloadType = type,
        payload = String(payloadData)
    )

    fun getAlternativeCarrierData(payloadData: ByteArray): AlternativeCarrier = AlternativeCarrier(
        payloadType = type,
        payload = String(payloadData)
    )

    fun getHandoverCarrierData(payloadData: ByteArray): HandoverCarrier = HandoverCarrier(
        payloadType = type,
        payload = String(payloadData)
    )

    fun getHandoverReceiveData(payloadData: ByteArray): HandoverReceive = HandoverReceive(
        payloadType = type,
        payload = String(payloadData)
    )

    fun getHandoverSelectData(payloadData: ByteArray): HandoverSelect = HandoverSelect(
        payloadType = type,
        payload = String(payloadData)
    )

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

/**
 * The TNF field value indicates the structure of the value of the TYPE field of the Ndef record.
 * The value 0x00 (Empty) indicates that there is no type or payload associated with this NDEF record.
 */

enum class TnfNameFormatter(val index: Int, val tnf: String) {
    TNF_EMPTY( 0x00,"Empty"),
    TNF_WELL_KNOWN(0x01,"NFC Forum well-known"),
    TNF_MIME_MEDIA(0x02,"Media-type"),
    TNF_ABSOLUTE_URI(0x03,"Absolute URI "),
    TNF_EXTERNAL_TYPE(0x04,"NFC Forum external"),
    TNF_UNKNOWN(0x05,"Unknown"),
    TNF_UNCHANGED(0x06,"Unchanged"),
    TNF_RESERVED(0x07,"Reserved");

    companion object {
        fun getTnfName(tnfValue: Int): String {
            return values().find { it.index == tnfValue }?.tnf ?: "Invalid Value"
        }
    }
}