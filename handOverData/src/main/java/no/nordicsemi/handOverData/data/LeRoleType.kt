package no.nordicsemi.handOverData.data

enum class LeRoleType {
    PERIPHERAL_ONLY,
    CENTRAL_ONLY,
    PERIPHERAL_CENTRAL_PERIPHERAL_PREFERRED,
    PERIPHERAL_CENTRAL_CENTRAL_PREFERRED,
    INVALID; // others reserved for future use

    companion object {
        private val mapping = mapOf<Byte, LeRoleType>(
            0x00.toByte() to PERIPHERAL_ONLY,
            0x01.toByte() to CENTRAL_ONLY,
            0x03.toByte() to PERIPHERAL_CENTRAL_PERIPHERAL_PREFERRED,
            0x04.toByte() to PERIPHERAL_CENTRAL_CENTRAL_PREFERRED
        )

        fun parse(byte: Byte): LeRoleType = mapping[byte] ?: INVALID
    }
}