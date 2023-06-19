package no.nordicsemi.domain.nfcTag.ndef

import android.os.Parcelable
import no.nordicsemi.domain.nfcTag.ndef.record.NdefRecordType
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcNdefMessage(
    val recordCount: Int,
    val currentMessageSize: Int,
    val maximumMessageSize: Int,
    val isNdefWritable: Boolean,
    val ndefRecord: List<NfcNdefRecord>,
    val ndefType: String
) : Parcelable {
    fun getAccessDataType(isNdefWritable: Boolean) =
        if (isNdefWritable) "Read/write" else "Read-only"
}

@Parcelize
data class NfcNdefRecord(
    val record: NdefRecordType? = null,
) : Parcelable

enum class NdefTagType(val type: String) {
    NFC_FORUM_TYPE_1("NFC Forum Type 1"),
    NFC_FORUM_TYPE_2("NFC Forum Type 2"),
    NFC_FORUM_TYPE_3("NFC Forum Type 3"),
    NFC_FORUM_TYPE_4("NFC Forum Type 4"),
    MIFARE_CLASSIC("MiFare Classic");

    companion object {
        fun getTagType(typeS: String): String {
            val typeMap = mapOf(
                "type1" to NFC_FORUM_TYPE_1.type,
                "type2" to NFC_FORUM_TYPE_2.type,
                "type3" to NFC_FORUM_TYPE_3.type,
                "type4" to NFC_FORUM_TYPE_4.type,
                "mifareclassic" to MIFARE_CLASSIC.type,
            )
            return typeMap[typeS.split(".").last()] ?: "Not supported Type"
        }
    }
}

/**
 * The TNF field value indicates the structure of the value of the TYPE field of the Ndef record.
 * The value 0x00 (Empty) indicates that there is no type or payload associated with this NDEF record.
 */
enum class TnfNameFormatter(val index: Int, val tnf: String) {
    TNF_EMPTY(0x00, "Empty"),
    TNF_WELL_KNOWN(0x01, "NFC Forum well-known type"),
    TNF_MIME_MEDIA(0x02, "Media-type"),
    TNF_ABSOLUTE_URI(0x03, "Absolute URI "),
    TNF_EXTERNAL_TYPE(0x04, "NFC Forum external type"),
    TNF_UNKNOWN(0x05, "Unknown"),
    TNF_UNCHANGED(0x06, "Unchanged"),
    TNF_RESERVED(0x07, "Reserved");

    companion object {
        fun getTnfName(tnfValue: Int): String {
            return values().find { it.index == tnfValue }?.tnf ?: "Invalid Value"
        }
    }
}