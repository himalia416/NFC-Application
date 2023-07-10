package no.nordicsemi.handOverData.data

// The following data type values are assigned by NFC Forum.
internal object BtHandOverType {

    /**
     * LE Bluetooth Device Address data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.3.
     */
    const val TYPE_MAC: Int = 0x1B

    /**
     * LE Role data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.3.
     */
    const val LE_ROLE: Int = 0x1C

    /**
     * Local Name data types 0x08 or 0x09. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.4.
     */
    const val LOCAL_NAME: Int = 0x09
    const val LOCAL_NAME_2: Int = 0x08

    /**
     * Security Manager TK Value data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.1.
     */
    const val SECURITY_MANAGER_TK: Int = 0x10
    const val SECURITY_MANAGER_TK_SIZE: Int = 16

    /**
     * LE Secure Connections Confirmation Value data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.5.
     */
    const val LE_SC_CONFIRMATION: Int = 0x22
    const val LE_SC_CONFIRMATION_SIZE: Int = 16

    /**
     * LE Secure Connections Random Value data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.6.
     */
    const val LE_SC_RANDOM: Int = 0x23
    const val LE_SC_RANDOM_SIZE: Int = 16

    /**
     * Appearance data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.2.
     */
    const val LE_APPEARANCE: Int = 0x19

    /**
     * Flags data type. For more details refer to Technical specification Bluetooth Secure Simple Pairing Using NFC Version 1.2 Section 3.4.3.
     */
    const val LE_FLAG: Int = 0x01

}