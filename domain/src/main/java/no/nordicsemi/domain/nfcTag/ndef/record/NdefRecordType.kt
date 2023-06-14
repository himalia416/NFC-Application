package no.nordicsemi.domain.nfcTag.ndef.record

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Javascript
import androidx.compose.material.icons.filled.MediaBluetoothOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface NdefRecordType : Parcelable

@Parcelize
sealed interface ExternalType : NdefRecordType

@Parcelize
data class TextRecord(
    val recordName: String = "Text Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val langCode: String = "",
    val encoding: String = "",
    val actualText: String = "",
    val payloadFieldName: String = "Text",
    val payloadData: ByteArray? = null
) : NdefRecordType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextRecord

        if (recordName != other.recordName) return false
        if (typeNameFormat != other.typeNameFormat) return false
        if (payloadType != other.payloadType) return false
        if (payloadLength != other.payloadLength) return false
        if (langCode != other.langCode) return false
        if (encoding != other.encoding) return false
        if (actualText != other.actualText) return false
        if (payloadFieldName != other.payloadFieldName) return false
        if (!payloadData.contentEquals(other.payloadData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordName.hashCode()
        result = 31 * result + typeNameFormat.hashCode()
        result = 31 * result + payloadType.hashCode()
        result = 31 * result + payloadLength
        result = 31 * result + langCode.hashCode()
        result = 31 * result + encoding.hashCode()
        result = 31 * result + actualText.hashCode()
        result = 31 * result + payloadFieldName.hashCode()
        result = 31 * result + payloadData.contentHashCode()
        return result
    }
}

@Parcelize
data class URIRecord(
    val recordName: String = "URI Record",
    val typeNameFormat: String = "",
    val payloadType: String? = null,
    val payloadLength: Int,
    val protocol: String? = null,
    val uri: String? = null,
    val actualUri: String = "",
    val payloadFieldName: String = "Actual URL",
    val payloadData: ByteArray? = null
) : NdefRecordType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as URIRecord

        if (recordName != other.recordName) return false
        if (typeNameFormat != other.typeNameFormat) return false
        if (payloadType != other.payloadType) return false
        if (payloadLength != other.payloadLength) return false
        if (protocol != other.protocol) return false
        if (uri != other.uri) return false
        if (actualUri != other.actualUri) return false
        if (payloadFieldName != other.payloadFieldName) return false
        if (payloadData != null) {
            if (other.payloadData == null) return false
            if (!payloadData.contentEquals(other.payloadData)) return false
        } else if (other.payloadData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordName.hashCode()
        result = 31 * result + typeNameFormat.hashCode()
        result = 31 * result + (payloadType?.hashCode() ?: 0)
        result = 31 * result + payloadLength
        result = 31 * result + (protocol?.hashCode() ?: 0)
        result = 31 * result + (uri?.hashCode() ?: 0)
        result = 31 * result + actualUri.hashCode()
        result = 31 * result + payloadFieldName.hashCode()
        result = 31 * result + (payloadData?.contentHashCode() ?: 0)
        return result
    }
}

@Parcelize
data class AlternativeCarrier(
    val recordName: String = "Alternative Carrier Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverCarrier(
    val recordName: String = "Handover Carrier Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverSelect(
    val recordName: String = "Handover Select Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
) : NdefRecordType

@Parcelize
data class HandoverReceive(
    val recordName: String = "Handover Receive Record",
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
data class AndroidApplicationRecord(
    val recordName: String = "Android Application Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val packageType: String = "Package Name",
    val payload: String = "",
    val payloadData: ByteArray? = null
) : ExternalType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AndroidApplicationRecord

        if (recordName != other.recordName) return false
        if (typeNameFormat != other.typeNameFormat) return false
        if (payloadType != other.payloadType) return false
        if (payloadLength != other.payloadLength) return false
        if (packageType != other.packageType) return false
        if (payload != other.payload) return false
        if (payloadData != null) {
            if (other.payloadData == null) return false
            if (!payloadData.contentEquals(other.payloadData)) return false
        } else if (other.payloadData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordName.hashCode()
        result = 31 * result + typeNameFormat.hashCode()
        result = 31 * result + payloadType.hashCode()
        result = 31 * result + payloadLength
        result = 31 * result + packageType.hashCode()
        result = 31 * result + payload.hashCode()
        result = 31 * result + (payloadData?.contentHashCode() ?: 0)
        return result
    }
}

@Parcelize
data class GenericExternalType(
    val recordName: String = "Externally Added Record",
    val typeNameFormat: String,
    val payloadType: String,
    val payloadLength: Int,
    val domain: String,
    val domainType: String?,
    val payload: String,
    val payloadData: ByteArray? = null
) : ExternalType {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenericExternalType

        if (recordName != other.recordName) return false
        if (typeNameFormat != other.typeNameFormat) return false
        if (payloadType != other.payloadType) return false
        if (payloadLength != other.payloadLength) return false
        if (domain != other.domain) return false
        if (domainType != other.domainType) return false
        if (payload != other.payload) return false
        if (payloadData != null) {
            if (other.payloadData == null) return false
            if (!payloadData.contentEquals(other.payloadData)) return false
        } else if (other.payloadData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordName.hashCode()
        result = 31 * result + typeNameFormat.hashCode()
        result = 31 * result + payloadType.hashCode()
        result = 31 * result + payloadLength
        result = 31 * result + domain.hashCode()
        result = 31 * result + (domainType?.hashCode() ?: 0)
        result = 31 * result + payload.hashCode()
        result = 31 * result + (payloadData?.contentHashCode() ?: 0)
        return result
    }
}

@Parcelize
data class SmartPoster(
    val recordName: String = "Smart Poster Record",
    val textRecord: TextRecord?,
    val uriRecord: URIRecord,
    val actionRecord: ActionRecord? = null,
) : NdefRecordType

@Parcelize
data class ActionRecord(
    val actionData: String,
    val actionType: String,
) : Parcelable

@Parcelize
data class MimeRecord(
    val recordName: String = "Mime Type Record",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
    val payloadData: ByteArray? = null
) : NdefRecordType {

    fun getRecordIcon(): ImageVector {
        val slicedPayloadType = payloadType.split('/')[0]
        return when {
            payloadType == "application/vnd.bluetooth.le.oob" -> Icons.Default.MediaBluetoothOn
            payloadType == "application/json" -> Icons.Default.Javascript
            payloadType == "application/pdf" -> Icons.Default.PictureAsPdf
            slicedPayloadType == "audio" -> Icons.Default.AudioFile
            slicedPayloadType == "text" -> Icons.Default.TextFormat
            slicedPayloadType == "video" -> Icons.Default.VideoFile
            slicedPayloadType == "message" -> Icons.Default.Message
            else -> Icons.Default.PermMedia
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MimeRecord

        if (recordName != other.recordName) return false
        if (typeNameFormat != other.typeNameFormat) return false
        if (payloadType != other.payloadType) return false
        if (payloadLength != other.payloadLength) return false
        if (payloadFieldName != other.payloadFieldName) return false
        if (payload != other.payload) return false
        if (payloadData != null) {
            if (other.payloadData == null) return false
            if (!payloadData.contentEquals(other.payloadData)) return false
        } else if (other.payloadData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordName.hashCode()
        result = 31 * result + typeNameFormat.hashCode()
        result = 31 * result + payloadType.hashCode()
        result = 31 * result + payloadLength
        result = 31 * result + payloadFieldName.hashCode()
        result = 31 * result + payload.hashCode()
        result = 31 * result + (payloadData?.contentHashCode() ?: 0)
        return result
    }
}
