package com.example.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface NdefRecordType : Parcelable

@Parcelize
data class TextRecordStructure(
    val payloadType: String = "",
    val langCode: String = "",
    val encoding: String = "",
    val actualText: String = "",
    val payloadFieldName: String = "Text",
    val recordName: String = "Text Field record"
) : NdefRecordType

@Parcelize
data class URIRecordStructure(
    val payloadType: String = "",
    val protocol: String = "",
    val actualUri: String = "",
    val payloadFieldName: String = "URI",
    val recordName: String = "URI Field record"
) : NdefRecordType

@Parcelize
data class SmartPoster(
    val recordName: String = "Smart Poster record",
    val payloadType: String = "",
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class AlternativeCarrier(
    val recordName: String = "Smart Poster record",
    val payloadType: String = "",
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverCarrier(
    val recordName: String = "Smart Poster record",
    val payloadType: String = "",
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverSelect(
    val recordName: String = "Smart Poster record",
    val payloadType: String = "",
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverReceive(
    val recordName: String = "Smart Poster record",
    val payloadType: String = "",
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
object Unknown : NdefRecordType

@Parcelize
data class AndroidPackage(
    val recordName: String = "Android Application record",
    val payloadType: String = "",
    val payloadFieldName: String = "Package",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class OtherExternalType(
    val recordName: String = "Externally Added record",
    val payloadType: String = "",
    val payloadFieldName: String = "Package",
    val payload: String = "",
): NdefRecordType