package no.nordicsemi.profile_nfc.utility

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun String.toSerialNumber(): String = this.chunked(2).joinToString(":").uppercase()