package no.nordicsemi.profile_nfc.utility

internal fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

internal fun String.toSerialNumber(): String = this.chunked(2).joinToString(":").uppercase()

internal fun ByteArray.toAtqaFormat(): String = "0x${this.toHex() }"

internal fun Short.toSakFormat() : String = "0x%02X".format(this)

internal fun ByteArray.toPayloadData() : String = this.toHex().chunked(2).joinToString(" ").uppercase()