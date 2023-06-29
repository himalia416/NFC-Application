package no.nordic.handOverSelectMessageParser

import android.bluetooth.BluetoothDevice

data class BluetoothLeOobData(
    // required fields
    val bleDeviceAddress: BluetoothDevice? = null,
    val bleAddressType: AddressType? = null,
    val roleType: LeRoleType? = null,
    // optional fields
    val securityManagerTK: ByteArray? = null,
    val leSecureConnectionConfirmation: ByteArray? = null,
    val leSecureConnectionRandom: ByteArray? = null,
    val appearance: String? = null,
    val flags: List<String> = emptyList(),
    val localName: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BluetoothLeOobData

        if (bleDeviceAddress != other.bleDeviceAddress) return false
        if (bleAddressType != other.bleAddressType) return false
        if (roleType != other.roleType) return false
        if (securityManagerTK != null) {
            if (other.securityManagerTK == null) return false
            if (!securityManagerTK.contentEquals(other.securityManagerTK)) return false
        } else if (other.securityManagerTK != null) return false
        if (leSecureConnectionConfirmation != null) {
            if (other.leSecureConnectionConfirmation == null) return false
            if (!leSecureConnectionConfirmation.contentEquals(other.leSecureConnectionConfirmation)) return false
        } else if (other.leSecureConnectionConfirmation != null) return false
        if (leSecureConnectionRandom != null) {
            if (other.leSecureConnectionRandom == null) return false
            if (!leSecureConnectionRandom.contentEquals(other.leSecureConnectionRandom)) return false
        } else if (other.leSecureConnectionRandom != null) return false
        if (appearance != other.appearance) return false
        if (flags != other.flags) return false
        if (localName != other.localName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bleDeviceAddress?.hashCode() ?: 0
        result = 31 * result + (bleAddressType?.hashCode() ?: 0)
        result = 31 * result + (roleType?.hashCode() ?: 0)
        result = 31 * result + (securityManagerTK?.contentHashCode() ?: 0)
        result = 31 * result + (leSecureConnectionConfirmation?.contentHashCode() ?: 0)
        result = 31 * result + (leSecureConnectionRandom?.contentHashCode() ?: 0)
        result = 31 * result + (appearance?.hashCode() ?: 0)
        result = 31 * result + flags.hashCode()
        result = 31 * result + (localName?.hashCode() ?: 0)
        return result
    }
}
