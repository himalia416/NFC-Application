package no.nordic.handOverSelectMessageParser.mapper

internal fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

internal fun String.toByteArray(): ByteArray = this.chunked(2)
.map { it.toInt(16).toByte() }
.toByteArray()