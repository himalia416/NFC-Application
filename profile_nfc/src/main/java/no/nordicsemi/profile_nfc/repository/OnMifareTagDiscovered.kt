package no.nordicsemi.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.util.Log
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicMessage
import no.nordicsemi.domain.nfcTag.MifareClassicTag
import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicTagType
import no.nordicsemi.profile_nfc.utility.toHex

object OnMifareTagDiscovered {
    val TAG = "MiFareClassicTag"
    fun parse(tag: Tag, generalTagInformation: GeneralTagInformation): MifareClassicTag? {
        val mifareClassic = MifareClassic.get(tag)
        mifareClassic?.let {
            mifareClassic.connect()
            val sectorCount = mifareClassic.sectorCount
            val mifareClassicTagType = mifareClassic.type
            val mifareClassicTagSize = mifareClassic.size

            Log.d(TAG, "onMifareClassicTagDiscovered: serial number: ${tag.id.toHex()}")
            Log.d(
                TAG, "onTagDiscovered: Number of sector $sectorCount " +
                        "\ntype: ${MifareClassicTagType.getTagType(mifareClassicTagType)} " +
                        "\nSize ${mifareClassic.size} " +
                        "\nnumber of blocks in each sector: ${mifareClassic.getBlockCountInSector(2)} " +
                        "\nBlock count: ${mifareClassic.blockCount}"
            )

            val mifareClassicMessage = MifareClassicMessage(
                sectorCount = sectorCount,
                tagType = MifareClassicTagType.getTagType(mifareClassicTagType),
                tagSize = mifareClassicTagSize,
                blockCount = mifareClassic.blockCount
            )

            return MifareClassicTag(generalTagInformation, mifareClassicMessage)
        }

        return null
    }
}
