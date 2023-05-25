package com.example.domain.nfcTag.ndef.record.mapper

/**
 * NFC Forum "URI Record Type Definition"
 * This is a mapping of "URI Identifier Codes" to URI string prefixes, per section 3.1.2 of the NFC Forum URI Record Type Definition document.
 * First index of the Payload (payload[0]) contains the URI Identifier Code, as per NFC Forum "URI Record Type Definition" section 3.1.2.
 * For example, if the value of the Identifier code is 0x02, and the content of the URI field reads as “nfc-forum.org”, the resulting URI is “https://www.nfc-forum.org”.
 * If the value of the Identifier code is zero (0x00), then prepending could not be done.
 * URI identifier code 36 to 255 (0x24..0xFF in Hex) are RFU: Reserved for Future Use.
 * All fields marked RFU are treated as if they were value zero (0x00, no prepending).
 * An NDEF application should not produce values that are marked RFU.
 */

object UriProtocolMapper {
    private val uriProtocolMap = mapOf(
        0x00 to "",
        0x01 to "http://www.",
        0x02 to "http://www.",
        0x03 to "http://",
        0x04 to "https://",
        0x05 to "tel:",
        0x06 to "mailto:",
        0x07 to "ftp://anonymous:anonymous@",
        0x08 to "ftp://ftp.",
        0x09 to "ftps://",
        0x0A to "sftp://",
        0x0B to "smb://",
        0x0C to "nfs://",
        0x0D to "ftp://",
        0x0E to "dav://",
        0x0F to "news:",
        0x10 to "telnet://",
        0x11 to "imap:",
        0x12 to "rtsp://",
        0x13 to "urn:",
        0x14 to "pop:",
        0x15 to "sip:",
        0x16 to "sips:",
        0x17 to "tftp:",
        0x18 to "btspp://",
        0x19 to "btl2cap://",
        0x1A to "btgoep://",
        0x1B to "tcpobex://",
        0x1C to "irdaobex://",
        0x1D to "file://",
        0x1E to "urn:epc:id:",
        0x1F to "urn:epc:tag:",
        0x20 to "urn:epc:pat:",
        0x21 to "urn:epc:raw:",
        0x22 to "urn:epc:",
        0x23 to "urn:nfc:",
        0x24..0xFF to ""
    )

    fun getUriPrefix(firstPayloadIndex: Int): String {
        return uriProtocolMap[firstPayloadIndex] ?: ""
    }
}
