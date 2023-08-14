package no.nordicsemi.domain.nfcTag.ndef.record

object RecordNameParser{

    /**
     * Parses the record type and returns the corresponding record name.
     */
    fun parse(recordType: String): String {
        return when (recordType) {
            "Di" -> "Device Information Record"
            "Hc" -> "Connection Handover"
            "Hi" -> "Connection Handover"
            "Hm" -> "Connection Handover"
            "Hr" -> "Connection Handover"
            "Hs" -> "Connection Handover"
            "Mr" -> "NFC Money Transfer"
            "Mt" -> "NFC Money Transfer"
            "PHD" -> "Personal Health Device Communication"
            "Sig" -> "Signature Record"
            "Sp" -> "Smart Poster Record"
            "T" -> "Text Record Type Definition"
            "Te" -> "Tag NDEF Exchange Protocol"
            "Tp" -> "Tag NDEF Exchange Protocol"
            "Ts" -> "Tag NDEF Exchange Protocol"
            "U" -> "URI Record"
            "V" -> "Verb Record"
            "WLCCAP" -> "Wireless Charging"
            "WLCCTL" -> "Wireless Charging"
            "WLCFOD" -> "Wireless Charging"
            "WLCINF" -> "Wireless Charging"
            else -> "Unknown"
        }
    }
}




