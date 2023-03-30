package com.example.nfcapplication.data

data class GeneralTagInformation(
    val serialNumber: String = "",
    val tagTechnology: List<String> = emptyList(),
    val icManufacturerName: String = "",
    val tagType: String = "",
    val maxTransceiveLength: Int = 0,
    val transceiveTimeout: String = "0 ms",
    val ndefMessage: NfcNdefMessage? = null
)

data class ManufacturerName(
    val identifier: String,
    val name: String,
)
