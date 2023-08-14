package no.nordicsemi.nfc.scanner.repository

import android.nfc.Tag
import android.nfc.tech.NfcV
import no.nordicsemi.nfc.domain.nfcTag.nfcV.NfcVInfo

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