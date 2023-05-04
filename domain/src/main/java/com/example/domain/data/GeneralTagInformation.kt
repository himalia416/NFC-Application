package com.example.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralTagInformation(
    val serialNumber: String = "",
    val tagTechnology: List<String> = emptyList(),
    val icManufacturerName: String = "",
    val tagType: String = "",
    val maxTransceiveLength: Int = 0,
    val transceiveTimeout: String = "0 ms",
    val ndefMessage: NfcNdefMessage? = null
) : Parcelable