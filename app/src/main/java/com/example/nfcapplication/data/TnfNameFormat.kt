package com.example.nfcapplication.data

enum class TnfNameFormatter(val tnf: String) {
    /*
    * For further details on this look into: https://developer.android.com/reference/android/nfc/NdefRecord#constants_1
    * */
    Empty("Empty"), // Indicates the record is empty.
    NFC_RTD("NFC Forum well-known type"),
    RFC_2046("Media-type [RFC 2046]"), // media-type
    RFC_3986("Absolute URI [RFC 3986]"),
    EXTERNAL_NFC_RTD("NFC Forum external type"), // Indicates the type field contains an external type name.
    Unknown("Unknown"),
    Unchanged("Unchanged"),
    Reserved("Reserved");

    companion object {
        fun getTnfName(tnfValue: Int): String {
            return values().find { it.ordinal == tnfValue }?.tnf ?: "Invalid Value"
        }
    }
}