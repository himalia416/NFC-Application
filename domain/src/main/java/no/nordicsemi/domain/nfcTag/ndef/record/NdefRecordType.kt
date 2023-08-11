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
import no.nordicsemi.handOverData.data.BluetoothLeOobData
import no.nordisemi.utils.DataByteArray

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
    val payloadData: DataByteArray? = null
) : NdefRecordType

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
    val payloadData: DataByteArray? = null
) : NdefRecordType

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
    val payloadData: DataByteArray? = null
) : ExternalType

@Parcelize
data class GenericExternalType(
    val recordName: String = "Externally Added Record",
    val typeNameFormat: String,
    val payloadType: String,
    val payloadLength: Int,
    val domain: String,
    val domainType: String?,
    val payload: String,
    val payloadData: DataByteArray? = null
) : ExternalType

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
    val payloadString: String? = null,
    val payloadData: DataByteArray? = null,
    val bluetoothLeOobData: BluetoothLeOobData? = null
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

}

@Parcelize
data class OtherRecords(
    val recordName: String = "",
    val typeNameFormat: String = "",
    val payloadType: String = "",
    val payloadLength: Int,
    val payloadFieldName: String = "Payload",
    val payload: String = "",
    val payloadData: DataByteArray? = null
) : NdefRecordType
