package no.nordicsemi.nfc.utils

/**
 * Converts ByteArray into Hex String.
 */
fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

/**
 * Converts String into the serial number format. Example: the string 5f47f12d40e2ad is converted into 5F:47:F1:2D:40:E2:AD.
 */
fun String.toSerialNumber(): String = this.chunked(2).joinToString(":").uppercase()

/**
 * Converts ByteArray into the Hex format. Example: "54b683417f535dccaa0e454a09c248ca" is converted into "54 B6 83 41 7F 53 5D CC AA 0E 45 4A 09 C2 48 CA".
 */
fun ByteArray.toPayloadData(): String = this.toHex().chunked(2).joinToString(" ").uppercase()

/**
 * Converts Hex String into atqa format. Example: 0044 is converted into 0x00444.
 */
fun ByteArray.toAtqaFormat(): String = "0x${this.toHex()}"

/**
 * Converts Hex String into SAK format. Example: 0 is converted into 0x00.
 */
fun Short.toSakFormat(): String = "0x%02X".format(this)

/**
 * Converts String into ByteArray.
 */
fun String.toByteArray(): ByteArray = this.chunked(2)
    .map { it.toInt(16).toByte() }
    .toByteArray()

/**
 * Converts ByteArray to Int.
 */
fun byteArrayToInt(bytes: ByteArray): Int {
    if (bytes.size > 4) {
        throw IllegalArgumentException("Byte array size must be 4 or less.")
    }

    var result = 0
    for (element in bytes) {
        result = (result shl 8) or (element.toInt() and 0xFF)
    }
    return result
}