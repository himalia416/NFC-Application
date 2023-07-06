package no.nordicsemi.nfcscanner.repository

import android.nfc.Tag
import android.nfc.tech.NfcA
import no.nordicsemi.domain.nfcTag.nfcA.NfcAInfo
import no.nordisemi.utils.toAtqaFormat
import no.nordisemi.utils.toSakFormat

object OnNfcATagDiscovered {

    /**
     * Parses NfcA tag
     */
    fun parse(tag: Tag): NfcAInfo? {
        val nfcA = NfcA.get(tag) ?: return null
        nfcA.connect()
        val nfcAInfo = NfcAInfo(
            atqa = nfcA.atqa.toAtqaFormat(),
            sak = nfcA.sak.toSakFormat(),
            maxTransceiveLength = nfcA.maxTransceiveLength,
            transceiveTimeout = nfcA.timeout
        )
        nfcA.close()
        return nfcAInfo
    }
}