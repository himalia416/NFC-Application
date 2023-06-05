package no.nordicsemi.profile_nfc.utility

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun String.toSerialNumber(): String = this.chunked(2).joinToString(":").uppercase()

fun ByteArray.toHexString(): String = "0x${this.joinToString("") { "%02X".format(it) }}"

fun Short.toHexString() : String = "0x%02X".format(this)