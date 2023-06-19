package no.nordicsemi.serialization.domain

import no.nordicsemi.domain.nfcTag.DiscoveredTag

interface NfcJsonAdapter {
    fun fromJson(jsonData: String): DiscoveredTag?
    fun toJson(tag: DiscoveredTag): String
}