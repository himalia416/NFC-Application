package no.nordicsemi.nfcscanner.utility

internal fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

internal fun ByteArray.toAtqaFormat(): String = "0x${this.toHex() }"

internal fun Short.toSakFormat() : String = "0x%02X".format(this)

