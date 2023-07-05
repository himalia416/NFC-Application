package no.nordic.handOverSelectMessageParser.parser

import kotlin.experimental.and

/**
 * Parses the Flag bytes.
 * The Flags data type described in Bluetooth Core Specification Section 1.3 Version 11, Bluetooth SIG, January 31, 2023.
 * The Flags data type contains one bit Boolean flags and is included when any of the Flag bits are non-zero.
 */
internal enum class FlagByteParser(val mask: Byte, val description: String) {
    LE_LIMITED_DISCOVERABLE_MODE(0x01, "LE Limited Discoverable Mode"),
    LE_GENERAL_DISCOVERABLE_MODE(0x02, "LE General Discoverable Mode"),
    BR_EDR_NOT_SUPPORTED(0x04, "BR/EDR Not Supported"),
    SIMULTANEOUS_LE_BR_EDR_CAPABLE(0x08, "Simultaneous LE and BR/EDR Capable");

    companion object {
        fun parse(flag: Byte): List<String> {
            val setBits = mutableListOf<String>()
            for (bit in values()) {
                if (flag and bit.mask != 0.toByte()) {
                    setBits.add(bit.description)
                }
            }
            return setBits
        }
    }
}