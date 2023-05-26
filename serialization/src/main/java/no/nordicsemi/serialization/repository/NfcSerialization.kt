package no.nordicsemi.serialization.repository

import no.nordicsemi.domain.nfcTag.NfcTag
import no.nordicsemi.serialization.domain.NfcJsonAdapter
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

internal class NfcSerialization @Inject constructor(
    private val jsonAdapter: JsonAdapter<NfcTag>
): NfcJsonAdapter {

    override fun fromJson(jsonData: String): NfcTag? {
        return jsonAdapter.fromJson(jsonData)
    }

    override fun toJson(tag: NfcTag): String {
        return (jsonAdapter.toJson(tag))
    }
}