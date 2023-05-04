package com.example.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcNdefMessage(
    val recordCount: Int,
    val currentMessageSize: Int,
    val maximumMessageSize: Int,
    val isNdefWritable: Boolean,
    val ndefRecord: List<NdefRecord>,
    val ndefType: String
) : Parcelable {
    fun getAccessDataType(isNdefWritable: Boolean) =
        if (isNdefWritable) "Read/write" else "Read-only"
}

@Parcelize
data class NdefRecord(
    val typeNameFormat: String = "",
    val type: String = "",
    val payloadLength: Int = 0,
    val payloadData: String = "",
) : Parcelable

