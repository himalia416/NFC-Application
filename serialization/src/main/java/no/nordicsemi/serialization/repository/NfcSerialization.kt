package no.nordicsemi.serialization.repository

import com.squareup.moshi.JsonAdapter
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.serialization.domain.NfcJsonAdapter
import javax.inject.Inject

internal class NfcSerialization @Inject constructor(
    private val jsonAdapter: JsonAdapter<DiscoveredTag>
) : NfcJsonAdapter {

    override fun fromJson(jsonData: String): DiscoveredTag? {
        return jsonAdapter.fromJson(jsonData)
    }

    override fun toJson(tag: DiscoveredTag): String {
        return (jsonAdapter.toJson(tag))
    }
}