package no.nordic.ui.utility

internal fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

internal fun String.toSerialNumber(): String = this.chunked(2).joinToString(":").uppercase()

internal fun ByteArray.toPayloadData() : String = this.toHex().chunked(2).joinToString(" ").uppercase()