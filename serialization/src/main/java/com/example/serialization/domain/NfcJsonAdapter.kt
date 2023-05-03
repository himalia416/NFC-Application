package com.example.serialization.domain

import com.example.domain.data.NfcTag

interface NfcJsonAdapter {
    fun fromJson(jsonData: String) : NfcTag?
    fun toJson(tag: NfcTag): String
}