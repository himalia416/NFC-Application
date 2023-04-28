package com.example.profile_nfc.data

data class NfcNdefMessage(
    val recordCount: Int,
    val currentMessageSize: Int,
    val maximumMessageSize: Int,
    val isNdefWritable: Boolean,
    val ndefRecord: List<NdefRecord>,
    val ndefType: String
) {
    fun getAccessDataType(isNdefWritable: Boolean) =
        if (isNdefWritable) "Read/write" else "Read-only"
}

data class NdefRecord(
    val typeNameFormat: String = "",
    val type: String = "",
    val payloadLength: Int = 0,
    val payloadData: String = "",
)
