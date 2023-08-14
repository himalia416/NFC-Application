package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.wellknowntype

internal enum class TnfWellKnown(val type: ByteArray) {
    RTD_TEXT(byteArrayOf(0x54)), // "T"
    RTD_URI(byteArrayOf(0x55)), // "U"
    RTD_SMART_POSTER(byteArrayOf(0x53, 0x70)), // "Sp"
    RTD_ALTERNATIVE_CARRIER(byteArrayOf(0x61, 0x63)), // "ac"
    RTD_HANDOVER_CARRIER(byteArrayOf(0x48, 0x63)),  // "Hc"
    RTD_HANDOVER_REQUEST(byteArrayOf(0x48, 0x72)),  // "Hr"
    RTD_HANDOVER_SELECT(byteArrayOf(0x48, 0x73)), // "Hs"
}
