package no.nordicsemi.nfcscanner.repository

import android.nfc.Tag
import android.nfc.tech.MifareClassic
import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicMessage
import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicTagType

object OnMifareTagDiscovered {

    /**
     * Parses MifareClassic tag.
     */
    fun parse(tag: Tag): MifareClassicMessage? {
        val mifareClassic = MifareClassic.get(tag) ?: return null

        mifareClassic.connect()
        val sectorCount = mifareClassic.sectorCount
        val mifareClassicTagType = mifareClassic.type
        val mifareClassicTagSize = mifareClassic.size

        val mifareClassicMessage = MifareClassicMessage(
            sectorCount = sectorCount,
            tagType = MifareClassicTagType.getTagType(mifareClassicTagType),
            tagSize = mifareClassicTagSize,
            blockCount = mifareClassic.blockCount
        )
        mifareClassic.close()

        return mifareClassicMessage
    }
}
