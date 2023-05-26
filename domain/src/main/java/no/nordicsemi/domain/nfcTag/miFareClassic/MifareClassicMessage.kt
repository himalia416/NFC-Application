package no.nordicsemi.domain.nfcTag.miFareClassic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MifareClassicMessage(
    val sectorCount: Int,
    val tagType: String,
    val tagSize: Int,
    val blockCount: Int,
    val sector: List<Sector> = emptyList(),
) : Parcelable

@Parcelize
data class Sector(
    val sectorCount: Int,
) : Parcelable

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