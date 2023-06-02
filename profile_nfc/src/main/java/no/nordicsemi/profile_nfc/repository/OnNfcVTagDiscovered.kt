package no.nordicsemi.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.NfcV
import no.nordicsemi.domain.nfcTag.NfcVInfo

object OnNfcVTagDiscovered {

    /**
     * Parses NfcV tag.
     */
    fun parse(tag: Tag): NfcVInfo?{
        val nfcV = NfcV.get(tag) ?: return null
        nfcV.connect()
        val nfcVInfo = NfcVInfo(
            dsfId = nfcV.dsfId.toString(),
            responseFlags = nfcV.responseFlags.toString(),
            maxTransceiveLength = nfcV.maxTransceiveLength
        )
        nfcV.close()
        return nfcVInfo
    }
}