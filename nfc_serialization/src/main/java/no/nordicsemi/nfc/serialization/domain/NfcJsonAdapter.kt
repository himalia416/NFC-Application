package no.nordicsemi.nfc.serialization.domain

import no.nordicsemi.nfc.domain.nfcTag.DiscoveredTag

interface NfcJsonAdapter {
    fun fromJson(jsonData: String): DiscoveredTag?
    fun toJson(tag: DiscoveredTag): String
}