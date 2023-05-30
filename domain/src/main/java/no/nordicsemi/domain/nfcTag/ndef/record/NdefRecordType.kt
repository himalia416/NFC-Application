package no.nordicsemi.domain.nfcTag.ndef.record

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface NdefRecordType : Parcelable

@Parcelize
data class TextRecord(
    val recordName: String = "Text Field Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val langCode: String = "",
    val encoding: String = "",
    val actualText: String = "",
    val payloadFieldName: String = "Text"
) : NdefRecordType

@Parcelize
data class URIRecord(
    val recordName: String = "URI Field Record",
    val typeNameFormat: String = "",
    val payloadType: String? = null,
    val payloadLength: Int,
    val protocol: String? = null,
    val uri: String? = null,
    val actualUri: String = "",
    val payloadFieldName: String = "Actual URL"
) : NdefRecordType

@Parcelize
data class SmartPoster(
    val recordName: String = "Smart Poster Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class AlternativeCarrier(
    val recordName: String = "Smart Poster Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverCarrier(
    val recordName: String = "Smart Poster Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverSelect(
    val recordName: String = "Smart Poster Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverReceive(
    val recordName: String = "Smart Poster Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class Unknown(
    val recordName: String = "Unknown"
) : NdefRecordType

@Parcelize
data class AndroidPackage(
    val recordName: String = "Android Application Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Package",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class OtherExternalType(
    val recordName: String = "Externally Added Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Package",
    val payload: String = "",
) : NdefRecordType