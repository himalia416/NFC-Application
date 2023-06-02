package no.nordicsemi.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.NfcB
import no.nordicsemi.domain.nfcTag.NfcBInfo

object OnNfcBTagDiscovered {

    /**
     * Parses NfcB tag.
     */
    fun parse(tag: Tag): NfcBInfo? {
        val nfcB = NfcB.get(tag) ?: return null
        nfcB.connect()
        val nfcBInfo = NfcBInfo(
            applicationData = nfcB.applicationData.toString(),
            maxTransceiveLength = nfcB.maxTransceiveLength,
            protocolInfo = nfcB.protocolInfo.toString()
        )
        nfcB.close()
        return nfcBInfo
    }
}