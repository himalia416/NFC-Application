package no.nordicsemi.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.NfcA
import no.nordicsemi.domain.nfcTag.NfcAInfo
import no.nordicsemi.profile_nfc.utility.toHex

object OnNfcATagDiscovered {

    /**
     * Parses NfcA tag
     */
    fun parse(tag: Tag): NfcAInfo? {
        val nfcA = NfcA.get(tag) ?: return null
        nfcA.connect()
        val nfcAInfo = NfcAInfo(
            atqa = nfcA.atqa.toHex(),
            sak = nfcA.sak,
            maxTransceiveLength = nfcA.maxTransceiveLength,
            transceiveTimeout = nfcA.timeout
        )
        nfcA.close()
        return nfcAInfo
    }
}