package com.example.nfcapplication.utility

import java.math.BigInteger

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
