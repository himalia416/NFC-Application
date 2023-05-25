package com.example.serialization.repository

import com.example.domain.nfcTag.NfcTag
import com.example.serialization.domain.NfcJsonAdapter
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