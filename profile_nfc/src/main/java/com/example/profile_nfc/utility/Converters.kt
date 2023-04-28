package com.example.profile_nfc.utility

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun serialNumberFormatter(input: String): String {
    return input.chunked(2).joinToString(":").uppercase()
}

fun ndefTypeFormatter(ndefType: String) =
    ndefType.split(".").last().replaceFirstChar(Char::titlecase)