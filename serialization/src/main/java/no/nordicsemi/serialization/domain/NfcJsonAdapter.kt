package no.nordicsemi.serialization.domain

import no.nordicsemi.domain.nfcTag.NfcTag

interface NfcJsonAdapter {
    fun fromJson(jsonData: String) : NfcTag?
    fun toJson(tag: NfcTag): String
}