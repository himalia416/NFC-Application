package com.example.nfcapplication.data

enum class TnfNameFormatter(val tnf: String) {
    Empty("Empty"),
    NFC_RTD("NFC Forum well-known type"),
    RFC_2046("Media-type [RFC 2046]"),
    RFC_3986("Absolute URI [RFC 3986]"),
    EXTERNAL_NFC_RTD("NFC Forum external type"),
    Unknown("Unknown"),
    Unchanged("Unchanged"),
    Reserved("Reserved");

    companion object {
        fun getTnfName(tnfValue: Int): String {
            return values().find { it.ordinal == tnfValue }?.tnf ?: "Invalid Value"
        }
    }
}