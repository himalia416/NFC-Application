package com.example.domain.mapper

import com.example.domain.data.AlternativeCarrier
import com.example.domain.data.AndroidPackage
import com.example.domain.data.HandoverCarrier
import com.example.domain.data.HandoverReceive
import com.example.domain.data.HandoverSelect
import com.example.domain.data.NdefRecordType
import com.example.domain.data.OtherExternalType
import com.example.domain.data.SmartPoster
import com.example.domain.data.TextRecord
import com.example.domain.data.TnfNameFormatter
import com.example.domain.data.URIRecord

// The RTD type name format is specified in NFCForum-TS-RTD_1.0.
object NdefRecordTypeMapper {
    private val RTD_TEXT = String(byteArrayOf(0x54)) // "T"
    private val RTD_URI = String(byteArrayOf(0x55)) // "U"
    private val RTD_SMART_POSTER = String(byteArrayOf(0x53, 0x70)) // "Sp"
    private val RTD_ALTERNATIVE_CARRIER = String(byteArrayOf(0x61, 0x63)) // "ac"
    private val RTD_HANDOVER_CARRIER = String(byteArrayOf(0x48, 0x63))  // "Hc"
    private val RTD_HANDOVER_REQUEST = String(byteArrayOf(0x48, 0x72))  // "Hr"
    private val RTD_HANDOVER_SELECT = String(byteArrayOf(0x48, 0x73)) // "Hs"
    private const val RTD_ANDROID_APP = "android.com:pkg"

    fun getNdefRecordType(
        typeNameFormat: String,
        type: String,
        payload: ByteArray
    ): NdefRecordType {
        return when (typeNameFormat) {
            TnfNameFormatter.TNF_WELL_KNOWN.tnf-> when (type) {
                RTD_TEXT -> getTextData(type, payload, typeNameFormat)
                RTD_URI -> getUriData(type, payload, typeNameFormat)
                RTD_SMART_POSTER -> getSmartPosterData(type, payload, typeNameFormat)
                RTD_ALTERNATIVE_CARRIER -> getAlternativeCarrierData(type, payload, typeNameFormat)
                RTD_HANDOVER_CARRIER -> getHandoverCarrierData(type, payload, typeNameFormat)
                RTD_HANDOVER_REQUEST -> getHandoverReceiveData(type, payload, typeNameFormat)
                RTD_HANDOVER_SELECT -> getHandoverSelectData(type, payload, typeNameFormat)
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> when (type) {
                RTD_ANDROID_APP -> getAndroidPackageData(type, payload, typeNameFormat)
                else -> OtherExternalType(payloadLength = payload.size)
            }

            else -> throw IllegalArgumentException("Unknown Record Type")
        }
    }

    private fun getTextData(type: String, payloadData: ByteArray, typeNameFormat: String): TextRecord {
        //status byte: bit 7 indicates encoding (0 = UTF-8, 1 = UTF-16)
        val isUTF8 = payloadData[0].toInt() and 0x080 == 0
        val encoding = if (isUTF8) Charsets.UTF_8 else Charsets.UTF_16
        //status byte: bits 5..0 indicate length of language code
        val languageLength = payloadData[0].toInt() and 0x03F
        val textLength = payloadData.size - 1 - languageLength
        val languageCode = String(payloadData, 1, languageLength, Charsets.US_ASCII)
        val payloadText = String(payloadData, 1 + languageLength, textLength, encoding)
        return TextRecord(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            langCode = languageCode,
            encoding = encoding.toString(),
            actualText = payloadText
        )
    }

    private fun getUriData(type: String, payloadData: ByteArray, typeNameFormat: String): URIRecord {
        val protocolField = UriProtocolMapper.getUriPrefix(payloadData[0].toInt())
        val actualPayload = String(payloadData)
        return URIRecord(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            protocol = protocolField,
            actualUri = actualPayload,
        )
    }

    private fun getAndroidPackageData(type: String, payloadData: ByteArray, typeNameFormat: String): AndroidPackage =
        AndroidPackage(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            payload = String(payloadData)
        )


    private fun getSmartPosterData(type: String, payloadData: ByteArray, typeNameFormat: String): SmartPoster = SmartPoster(
        typeNameFormat = typeNameFormat,
        payloadType = type,
        payloadLength = payloadData.size,
        payload = String(payloadData)
    )

    private fun getAlternativeCarrierData(
        type: String,
        payloadData: ByteArray, typeNameFormat: String
    ): AlternativeCarrier = AlternativeCarrier(
        typeNameFormat = typeNameFormat,
        payloadType = type,
        payloadLength = payloadData.size,
        payload = String(payloadData)
    )

    private fun getHandoverCarrierData(type: String, payloadData: ByteArray, typeNameFormat: String): HandoverCarrier =
        HandoverCarrier(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            payload = String(payloadData)
        )

    private fun getHandoverReceiveData(type: String, payloadData: ByteArray, typeNameFormat: String): HandoverReceive =
        HandoverReceive(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            payload = String(payloadData)
        )

    private fun getHandoverSelectData(type: String, payloadData: ByteArray, typeNameFormat: String): HandoverSelect =
        HandoverSelect(
            typeNameFormat = typeNameFormat,
            payloadType = type,
            payloadLength = payloadData.size,
            payload = String(payloadData)
        )
}
