package com.example.nfcapplication.repository

import com.example.nfcapplication.data.GeneralTagInformation

data class MifareClassicTag(
    override val general: GeneralTagInformation,
    val mifareClassicField1: MifareClassicMessage
) : NfcTag

data class MifareClassicMessage(
    val sectorCount: Int,
    val tagType: String,
    val tagSize: Int,
    val blockCount: Int,
    val sector: List<Sector> = emptyList(),
)

data class Sector(
    val sectorCount: Int,
)

enum class MifareClassicTagType(val type: String) {
    TYPE_UNKNOWN("Type Unknown"),
    TYPE_CLASSIC("MifareClassic"),
    TYPE_PLUS("MifareClassic Plus"),
    TYPE_PRO("MifareClassic Pro");

    companion object {
        fun getTagType(tagType: Int): String {
            return values().find { (it.ordinal - 1) == tagType }?.type ?: "Invalid Value"
        }
    }
}