package no.nordicsemi.handOverData.data

internal enum class AddressType(val value: String) {
    PRIVATE("Random Device Address"),
    PUBLIC("Public Address"),
    UNKNOWN ("Unknown");

    companion object {
        fun parse(byte: Byte): String = when (byte.toInt()) {
            0 -> PUBLIC.value
            1 -> PRIVATE.value
            else -> UNKNOWN.value
        }
    }
}