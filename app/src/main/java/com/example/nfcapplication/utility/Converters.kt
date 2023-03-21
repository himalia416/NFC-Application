package com.example.nfcapplication.utility

import java.math.BigInteger

fun bytesToHexString(bytes: ByteArray?): String {
    val hexString = StringBuilder()
    if (bytes != null) {
        for (byte in bytes) {
            val hex = Integer.toHexString(0xFF and byte.toInt())
            if (hex.length == 1) {
                // Add leading zero if hex has only one digit
                hexString.append('0')
            }
            hexString.append(hex)
        }
    }
    return hexString.toString()
}

fun bytesToHex(bytes: ByteArray?): String {
    val bigInteger = BigInteger(1, bytes)
    val hexString = bigInteger.toString(16)
    val paddingLength = ((bytes?.size ?: 0) * 2) - hexString.length
    return if (paddingLength > 0) {
        "0".repeat(paddingLength) + hexString
    } else {
        hexString
    }
}
