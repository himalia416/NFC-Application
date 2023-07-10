package no.nordicsemi.nfcscanner.repository

import android.nfc.Tag
import android.nfc.tech.NfcF
import no.nordicsemi.domain.nfcTag.nfcF.NfcFInfo

object OnNfcFTagDiscovered {

    /**
     * Parses NfcF tag.
     */
    fun parse(tag: Tag): NfcFInfo?{
        val nfcF = NfcF.get(tag) ?: return null
        nfcF.connect()
        val nfcFInfo = NfcFInfo(
            manufacturerByte = nfcF.manufacturer.toString(),
            systemCode = nfcF.systemCode.toString(),
            maxTransceiveLength = nfcF.maxTransceiveLength,
            transceiveTimeout = nfcF.timeout
        )
        nfcF.close()
        return nfcFInfo
    }
}