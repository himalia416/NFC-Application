package no.nordicsemi.nfc.handover.data

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.nfc.utils.DataByteArray

@Parcelize
data class BluetoothLeOobData(
    // required fields
    val bleDeviceAddress: BluetoothDevice,
    val bleAddressType: String,
    val roleType: LeRoleType,
    // optional fields
    val securityManagerTK: DataByteArray? = null,
    val leSecureConnectionConfirmation: DataByteArray? = null,
    val leSecureConnectionRandom: DataByteArray? = null,
    val appearance: String? = null,
    val flags: List<String> = emptyList(),
    val localName: String? = null,
) : Parcelable