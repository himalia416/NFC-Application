package no.nordicsemi.bleconnection

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.android.common.navigation.createDestination
import no.nordicsemi.android.common.navigation.defineDestination
import no.nordicsemi.bleconnection.view.ConnectBluetoothScreen

@Parcelize
data class BleDevice(
    val device: String
) : Parcelable

val NfcBleDeviceDestinationId = createDestination<BleDevice, Unit>("nfc-ble-screen")

val NfcBleDeviceScreenDestination = defineDestination(NfcBleDeviceDestinationId) {
    ConnectBluetoothScreen()
}